package com.zsmx.vod.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsmx.exception.GgktException;
import com.zsmx.model.vod.Teacher;
import com.zsmx.result.Result;
import com.zsmx.vo.vod.TeacherQueryVo;
import com.zsmx.vod.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author zsmx
 * @since 2023-12-30
 */
@Tag(name="讲师管理接口")
@RestController
@RequestMapping("/admin/vod/teacher")
@CrossOrigin
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    //批量删除
    @Operation(summary = "batchRemove-批量删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> id){
        teacherService.removeByIds(id);
        return Result.ok(null);
    }

    @Operation(summary = "get-通过id获取讲师")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        Teacher teacher = teacherService.getById(id);
        return Result.ok(teacher);
    }

    @Operation(summary = "update-通过id修改讲师信息")
    @PutMapping("update")
    public Result updateById(@RequestBody Teacher teacher) {
        teacherService.updateById(teacher);
        return Result.ok(null);
    }

    //新增讲师
    @Operation(summary = "save-新增讲师",description = "新增讲师")
    @PostMapping("save")
    public Result save(@RequestBody Teacher teacher){
        boolean save = teacherService.save(teacher);
        return Result.ok(save).message("新增成功");
    }

    //条件查询分页列表
    @Operation(summary = "findPage-条件查询分页列表",description = "条件查询分页列表")
    @PostMapping("findQueryPage/{page}/{limit}")
    public Result findPage(
            @Parameter(name = "page",description = "当前页码", required = true)
            @PathVariable Long page,
            @Parameter(name="limit",description = "每页记录数", required = true)
            @PathVariable Long limit,
            @Parameter(name = "teacherQuery",description = "查询对象", required = false)
            @RequestBody(required = false)TeacherQueryVo teacherQueryVo){
        //创建page对象，传递当前页和每页记录数
        Page<Teacher> pageParam = new Page<>(page,limit);
        //获取条件值
        String name = teacherQueryVo.getName();//讲师名称
        Integer level = teacherQueryVo.getLevel();//讲师级别
        String joinDateBegin = teacherQueryVo.getJoinDateBegin();//开始时间
        String joinDateEnd = teacherQueryVo.getJoinDateEnd();//结束时间
        //封装条件，判断条件值是否为空，不为空则进行封装
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(joinDateBegin)){
            wrapper.ge("join_date",joinDateBegin);
        }
        if(!StringUtils.isEmpty(joinDateEnd)){
            wrapper.le("join_date",joinDateEnd);
        }
        //调用方法得到分页查询结果
        IPage<Teacher> pageModel = teacherService.page(pageParam, wrapper);
        return Result.ok(pageModel);
    }

    // http://localhost:9301/admin/vod/teacher/findAll
    //查询所有讲师列表
    @Operation(summary = "findAll-查询讲师列表",description = "查询讲师列表")
    @GetMapping("findAll")
    public Result findAll(){
        //模拟异常
        //try {
        //    int i = 10/0;
        //}
        //catch (Exception e){
        //    throw new GgktException(201,"执行了自定义异常处理");
        //}
        List<Teacher> list = teacherService.list();
        return Result.ok(list).message("查询成功");
    }

    //删除讲师
    @Operation(summary = "removeById-删除讲师",description = "删除讲师")
    @DeleteMapping("remove/{id}")
    public Result removeById(@Parameter(name = "id",description = "ID",required = true)
                             @PathVariable String id){
        boolean isSuccess = teacherService.removeById(id);
        if(isSuccess){
            return Result.ok().message("删除成功");
        }else {
            return Result.fail().message("删除失败");
        }
    }


}

