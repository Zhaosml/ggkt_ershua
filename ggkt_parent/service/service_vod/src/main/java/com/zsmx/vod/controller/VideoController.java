package com.zsmx.vod.controller;


import com.zsmx.model.vod.Video;
import com.zsmx.result.Result;
import com.zsmx.vod.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author zsmx
 * @since 2024-01-08
 */
@Tag(name = "课程小结（课时")
@RestController
@RequestMapping("/admin/vod/video")
@CrossOrigin
public class VideoController {
    @Autowired
    private VideoService videoService;

    //根据id查询信息
    @Operation(summary = "get-根据id查询信息")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id){
        Video video = videoService.getById(id);
        return  Result.ok(video).message("查询成功");
    }
    //增
    @Operation(summary = "save-新增")
    @PostMapping("save")
    public Result save(@RequestBody Video video) {
        videoService.save(video);
        return Result.ok(video);
    }
    //删
    @Operation(summary = "remove-删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        videoService.removeVideoById(id);
        return Result.ok(null);
    }
    //改
    @Operation(summary = "update-修改")
    @PutMapping("update")
    public Result updateById(@RequestBody Video video) {
        videoService.updateById(video);
        return Result.ok(video);
    }
}

