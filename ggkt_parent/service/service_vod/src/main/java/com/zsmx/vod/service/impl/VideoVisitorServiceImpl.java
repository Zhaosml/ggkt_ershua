package com.zsmx.vod.service.impl;

import com.zsmx.model.vod.VideoVisitor;
import com.zsmx.vo.vod.VideoVisitorCountVo;
import com.zsmx.vod.mapper.VideoVisitorMapper;
import com.zsmx.vod.service.VideoVisitorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 视频来访者记录表 服务实现类
 * </p>
 *
 * @author zsmx
 * @since 2024-01-12
 */
@Service
public class VideoVisitorServiceImpl extends ServiceImpl<VideoVisitorMapper, VideoVisitor> implements VideoVisitorService {
    //显示统计数据
    @Override
    public Map<String, Object> findCount(Long courseId, String startDate, String endDate) {
        //调用mapper方法
        List<VideoVisitorCountVo> videoVisitorVoList = baseMapper.findCount(courseId,startDate,endDate);
        //创建mapper集合
        Map<String,Object> map = new HashMap<>();
        //创建两个list集合，一个代表所有日期，一个代表日期对应数量
        //封装数据 代表所有日期
        List<String> dateList = videoVisitorVoList
                .stream()
                .map(VideoVisitorCountVo::getJoinTime)
                .collect(Collectors.toList());
        //代表日期对应数量
        List<Integer> countList = videoVisitorVoList
                .stream()
                .map(VideoVisitorCountVo::getUserCount)
                .collect(Collectors.toList());
        //放到map集合
        map.put("xData", dateList);
        map.put("yData", countList);

        return map;
    }
}
