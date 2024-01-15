package com.zsmx.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zsmx.model.base.BaseEntity;
import com.zsmx.model.vod.Chapter;
import com.zsmx.model.vod.Video;
import com.zsmx.vo.vod.ChapterVo;
import com.zsmx.vo.vod.VideoVo;
import com.zsmx.vod.mapper.ChapterMapper;
import com.zsmx.vod.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsmx.vod.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zsmx
 * @since 2024-01-08
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {
    @Autowired
    private VideoService videoService;
    //章节小结列表封装
    @Override
    public List<ChapterVo> getNestedTreeList(Long courseId) {
        //这里查询了  章节表  和   课程视频表
        List<ChapterVo> chapterVoList = new ArrayList<>();

        //获取章节信息
        LambdaQueryWrapper<Chapter> queryWrapperChapter = new LambdaQueryWrapper<>();
        queryWrapperChapter.eq(Chapter::getCourseId, courseId);
        queryWrapperChapter.orderByAsc(Chapter::getSort, Chapter::getId);
        List<Chapter> chapterList = baseMapper.selectList(queryWrapperChapter);
        //QueryWrapper wrapper = new QueryWrapper();
        //wrapper.eq("course_id",courseId);
        //List<Chapter> chapterList = baseMapper.selectList(wrapper);

        //获取课时信息
        LambdaQueryWrapper<Video> wrapperVideo = new LambdaQueryWrapper<>();
        wrapperVideo.eq(Video::getCourseId,courseId);//课程视频的课程id
        wrapperVideo.orderByAsc(Video::getSort,Video::getId);//通过课程视频的id进行排序
        List<Video> videoList = videoService.list(wrapperVideo);//查询 课程视频表 全部
        //填充列表数据：Chapter列表
        for(int i = 0; i < chapterList.size(); i++) {//chapterList.size()  元素数量
            Chapter chapter = chapterList.get(i);//获取与当前索引相同的id信息
            //创建ChapterVo对象
            ChapterVo chapterVo = new ChapterVo();//ChapterVo包含了 chapter(章节表) 和 video(课时表)
            BeanUtils.copyProperties(chapter, chapterVo);//复制 chapter实体类的信息 到chapterVo实体类
            chapterVoList.add(chapterVo);//chapterVo实体类的信息 保存到  List<ChapterVo>列表

            //填充列表数据：Video列表
            List<VideoVo> videoVoList = new ArrayList<>();//获取VideoVo VO类 的列表
            for (int j = 0; j < videoList.size(); j++) {//videoList.size() 元素数量
                Video video = videoList.get(j);//获取与当前索引相同的id信息
                if (chapter.getId().equals(video.getChapterId())){//当章节表的id等于课时表的章节外键id时
                    VideoVo videoVo = new VideoVo();//创建VideoVo VO类 实例
                    BeanUtils.copyProperties(video,videoVo);//复制video实体类的信息 到 videoVo Vo类
                    videoVoList.add(videoVo);//把videoVo Vo类的信息保存到   List<VideoVo>列表
                }
            }
            chapterVo.setChildren(videoVoList);//设置chapterVo 章节下的课时列表是  List<VideoVo>列表
        }
            return chapterVoList;
    }
    //CourseServiceImpl会调用此方法
    @Override
    public void removeChapterByCourseId(Long id) {
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    }
}
