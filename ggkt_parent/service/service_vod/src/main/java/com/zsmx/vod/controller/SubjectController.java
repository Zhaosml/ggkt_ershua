package com.zsmx.vod.controller;


import com.zsmx.model.vod.Subject;
import com.zsmx.result.Result;
import com.zsmx.vod.service.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author zsmx
 * @since 2024-01-06
 */
@Tag(name = "课程分类管理")
@RestController
@RequestMapping("/admin/vod/subject")
@CrossOrigin
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @Operation(summary = "查询下一层的课程分类")
    @GetMapping("getChildSubject/{id}")
    public Result getChildSubject(@PathVariable Long id){
       List<Subject> list = subjectService.selectList(id);
       return Result.ok(list);
    }

    @Operation(summary = "excel导出")
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response){
        subjectService.exportData(response);
    }

    //课程分类导入
    @Operation(summary = "excel导入")
    @PostMapping("importData")
    public Result importData(MultipartFile file) {
        subjectService.importData(file);
        return Result.ok(file);
    }

}

