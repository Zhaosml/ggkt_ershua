package com.zsmx.vod.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsmx.model.vod.Course;
import com.zsmx.result.Result;
import com.zsmx.vo.vod.CourseFormVo;
import com.zsmx.vo.vod.CoursePublishVo;
import com.zsmx.vo.vod.CourseQueryVo;
import com.zsmx.vod.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zsmx
 * @since 2024-01-08
 */
@Tag(name = "课程管理接口")
@RestController
@RequestMapping("/admin/vod/course")
@CrossOrigin
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Operation(summary = "获取分页列表")
    @GetMapping("/getPageList/{page}/{limit}")
    public Result getPageList(
            @Parameter(name = "page",description = "当前页码",required = true)
            @PathVariable Long page,
            @Parameter(name = "limit",description = "每页记录数",required = true)
            @PathVariable Long limit,
            @Parameter(name = "courseVo",description = "查询对象vo",required = false)
            CourseQueryVo courseQueryVo) {
        //mybatis的page分页
        Page<Course> pageParam = new Page<>(page,limit);
        Map<String,Object> map = courseService.findPage(pageParam,courseQueryVo);
        return Result.ok(map);
    }
    //添加课程基本信息
    @Operation(summary = "save-增加课程基本信息")
    @PostMapping("save")
    public Result save(@RequestBody CourseFormVo courseFormVo){
        Long courseId = courseService.saveCourseInfo(courseFormVo);
        return Result.ok(courseId);
    }

    //根据id获取课程信息
    @Operation(summary = "根据id获取课程信息")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id){
        CourseFormVo course = courseService.getCourseFormVoById(id);
        return Result.ok(course);
    }
    //根据id修改课程信息
    @Operation(summary = "根据id修改课程信息")
    @PutMapping("update")
    public Result updateById(@RequestBody CourseFormVo courseFormVo){
        courseService.updateCourseId(courseFormVo);
        //TODO return Result.ok(courseFormVo.getId());的getId()是非常重要的，
        // 因为当子组件查询父组件的courseId时，我们父组件必须要的
        // 报错经历:打开修改课程列表，点击下一步，查询不到父组件的id。原因就是在这里没写返回值
        return Result.ok(courseFormVo.getId());
    }

    //步骤三-最终发布
    @Operation(summary = "根据id获取课程最终发布")
    @GetMapping("getCoursePublishVo/{id}")
    public Result getCoursePublishVo(@Parameter(description = "课时",required = true)
                                     @PathVariable Long id){
        CoursePublishVo coursePublishVo = courseService.getCoursePublishVo(id);
        return Result.ok(coursePublishVo);
    }
    //步骤三-最终发布   根据id发布课程
    @Operation(summary = "根据id发布课程")
    @PutMapping("publishCourseById/{id}")
    public Result publishCourseById(
            @Parameter(description = "课程ID", required = true)
            @PathVariable Long id){

        boolean result = courseService.publishCourseById(id);
        return Result.ok();
    }

    @Operation(summary = "删除课程")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        courseService.removeCourseById(id);
        return Result.ok();
    }
}

