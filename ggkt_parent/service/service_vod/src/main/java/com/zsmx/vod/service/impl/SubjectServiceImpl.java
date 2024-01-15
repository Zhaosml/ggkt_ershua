package com.zsmx.vod.service.impl;



import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsmx.exception.GgktException;
import com.zsmx.model.vod.Subject;
import com.zsmx.vo.vod.SubjectEeVo;
import com.zsmx.vo.vod.SubjectVo;
import com.zsmx.vod.listener.SubjectListener;
import com.zsmx.vod.mapper.SubjectMapper;
import com.zsmx.vod.service.SubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author zsmx
 * @since 2024-01-06
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Autowired
    private SubjectListener subjectListener;
    @Override
    public List<Subject> selectList(Long id) {
        //select * from Subject where parent_id
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("parent_id",id);
        List<Subject> subjectList = baseMapper.selectList(wrapper);
        //subjectslist遍历，得到每一个subject对象，判断是否有下一层，有haschildren=true
        //封装下一层数据
        for(Subject subject:subjectList){
            //获取subject的id值
            Long subjectId = subject.getId();
            //查询
            boolean children = this.isChildren(subjectId);
            //封装到对象里面
            subject.setHasChildren(children);
        }
        return subjectList;
    }


    //判断是否有下一层数据    //判断id下面是否有子节点
    private boolean isChildren(Long subjectId){
        QueryWrapper<Subject> wrapper = new QueryWrapper();
        wrapper.eq("parent_id",subjectId);
        Integer count = baseMapper.selectCount(wrapper);
        return count>0;
    }

    //excel导出
    @Override
    public void exportData(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("课程分类", "utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            List<Subject> subjectList = baseMapper.selectList(null);
            List<SubjectEeVo> subjectVos = new ArrayList<>(subjectList.size());
            for (Subject subject : subjectList) {
                SubjectEeVo subjectVo = new SubjectEeVo();
                //把一个类的值复制到另一个类
                BeanUtils.copyProperties(subject,subjectVo);
                subjectVos.add(subjectVo);
            }
            EasyExcel.write(response.getOutputStream(),SubjectVo.class)
                    .sheet("课程分类")
                    .doWrite(subjectVos);
        }
        catch (Exception e){
            throw new GgktException(20001,"导出失败");
        }
    }
    //excel导入
    @Override
    public void importData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(),SubjectEeVo.class,subjectListener)
                    .sheet()
                    .doRead();
        }
        catch (Exception e){
            throw new GgktException(20001,"导入失败");
        }
    }
}
