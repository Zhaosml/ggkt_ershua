package com.zsmx.vod.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.zsmx.model.vod.Subject;
import com.zsmx.vo.vod.SubjectEeVo;
import com.zsmx.vod.mapper.SubjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;


/**
 * @author 钊思暮想
 * @date 2024/1/7 17:38
 */
@Component
public class SubjectListener extends AnalysisEventListener<SubjectEeVo> {
    //excel导入这一集要复习
    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public void invoke(SubjectEeVo subjectEeVo, AnalysisContext analysisContext) {
        Subject subject = new Subject();
        BeanUtils.copyProperties(subjectEeVo,subject);
        subjectMapper.insert(subject);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
