package com.zsmx.vod.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsmx.model.vod.VideoVisitor;
import com.zsmx.vo.vod.VideoVisitorCountVo;

import java.util.List;

/**
 * <p>
 * 视频来访者记录表 Mapper 接口
 * </p>
 *
 * @author zsmx
 * @since 2024-01-12
 */
public interface VideoVisitorMapper extends BaseMapper<VideoVisitor> {

    List<VideoVisitorCountVo> findCount(Long courseId, String startDate, String endDate);
}
