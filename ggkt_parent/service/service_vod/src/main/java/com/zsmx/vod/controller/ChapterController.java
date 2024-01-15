package com.zsmx.vod.controller;


import com.zsmx.model.vod.Chapter;
import com.zsmx.result.Result;
import com.zsmx.vo.vod.ChapterVo;
import com.zsmx.vod.service.ChapterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zsmx
 * @since 2024-01-08
 */
@Tag(name = "章节管理")
@RestController
@RequestMapping("/admin/vod/chapter")
@CrossOrigin
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    //获取章节小节信息
    @Operation(summary = "获取章节信息")
    @GetMapping("getNestedTreeList/{courseId}")
    public Result getNestedTreeList(
                      @Parameter(description = "课程id" )
                      @PathVariable Long courseId){
       List<ChapterVo> list = chapterService.getNestedTreeList(courseId);
       return Result.ok(list);
    }

    //添加章节
    @Operation(summary = "save-添加章节信息")
    @PostMapping("save")
    public Result save(@RequestBody Chapter chapter){
         chapterService.save(chapter);
         return Result.ok(chapter).message("增加成功");
    }
    //根据id查询章节信息 -修改 查询id
    @Operation(summary = "get-根据id查询章节信息")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id){
        Chapter chapter = chapterService.getById(id);
        return Result.ok(chapter);
    }
    //根据id修改章节信息   -修改 最终实现
    @Operation(summary = "update-根据id修改课程信息")
    @PutMapping("update")
    public Result update(@RequestBody Chapter chapter){
        chapterService.updateById(chapter);
        return Result.ok(chapter);
    }
    //根据id删除章节信息    -删除
    @Operation(summary = "remove-根据id删除章节信息")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id){
        chapterService.removeById(id);
        return Result.ok();
    }
}

