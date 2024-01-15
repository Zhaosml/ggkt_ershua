package com.zsmx.vod.controller;


import com.zsmx.model.vod.Video;
import com.zsmx.model.vod.VideoVisitor;
import com.zsmx.result.Result;
import com.zsmx.vod.service.VideoService;
import com.zsmx.vod.service.VideoVisitorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.yaml.snakeyaml.events.Event;

import java.util.Map;

/**
 * <p>
 * 视频来访者记录表 前端控制器
 * </p>
 *
 * @author zsmx
 * @since 2024-01-12
 */
@Tag(name = "视频访问者管理")
@RestController
@RequestMapping("/admin/vod/videoVisitor")
@CrossOrigin
public class VideoVisitorController {
    @Autowired
    private VideoVisitorService videoVisitorService;

    @Operation(summary = "显示课程统计")
    @GetMapping("findCount/{courseId}/{startDate}/{endDate}")
    public Result get(@Parameter(description = "开始时间") @PathVariable Long courseId,
                      @Parameter(description = "开始时间") @PathVariable String startDate,
                      @Parameter(description = "结束时间") @PathVariable String endDate){
        Map<String,Object> map = videoVisitorService.findCount(courseId,startDate,endDate);
        return Result.ok(map);
    }

}

