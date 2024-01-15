package com.zsmx.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsmx.model.vod.Course;
import com.zsmx.model.vod.CourseDescription;
import com.zsmx.model.vod.Subject;
import com.zsmx.model.vod.Teacher;
import com.zsmx.vo.vod.CourseFormVo;
import com.zsmx.vo.vod.CoursePublishVo;
import com.zsmx.vo.vod.CourseQueryVo;
import com.zsmx.vod.mapper.CourseMapper;
import com.zsmx.vod.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.Put;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zsmx
 * @since 2024-01-08
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private CourseDescriptionService courseDescriptionService;

    //远程调用VideoService的方法
    @Autowired
    private VideoService videoService;
    @Autowired
    private ChapterService chapterService;

    //分页查询   课程列表
    @Override
    public Map<String, Object> findPage(Page<Course> pageParam, CourseQueryVo courseQueryVo) {
        //1.获取条件值
        Long teacherId = courseQueryVo.getTeacherId();//讲师id
        Long subjectId = courseQueryVo.getSubjectId();//一级分类
        Long subjectParentId = courseQueryVo.getSubjectParentId();//二级分类
        String title = courseQueryVo.getTitle();//标题    查询like
        //2.使用QueryWrapper封装条件
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(teacherId)){
            wrapper.eq("teacherId",teacherId);
        }
        //TODO wrapper.eq("subjectId",subjectId)写了这个，在前端数据条件查询的时候，
        // 在数据库找不到subject_id，然后报错
        if(!StringUtils.isEmpty(subjectId)){
            wrapper.eq("subject_id",subjectId);
        }
        //TODO 这个也是不能写subjectParentId
        if(!StringUtils.isEmpty(subjectParentId)){
            wrapper.eq("subject_parent_id",subjectParentId);
        }
        //3.调用方法查询
        Page<Course> coursePage = baseMapper.selectPage(pageParam, wrapper);
        long totalCount = coursePage.getTotal();//总记录数
        long totalPage = coursePage.getPages();//总页数
        long currentPage = coursePage.getCurrent();//当前页
        long size = coursePage.getSize();//每页记录数
        //4.每页数据的集合
        List<Course> records = coursePage.getRecords();//每页数据的集合
        //5.遍历封装的讲师和分页名称
        records.stream().forEach(item ->{
            //getTeacherOrSubjectName  获取讲师和分类名称
            this.getTeacherOrSubjectName(item);
        });
        //6.封装返回的结果
        Map<String,Object> map = new HashMap<>();
        map.put("totalCount",totalCount);
        map.put("totalPage",totalPage);
        map.put("records",records);

        return map;
    }



    //获取讲师和分类名称
    private Course getTeacherOrSubjectName(Course course) {
        //查询讲师名称
        Teacher teacher = teacherService.getById(course.getTeacherId());
        if(teacher != null){
            //getParam()  这个不在实体类里  是实力类继承了BaseEntity  表示其他参数
            course.getParam().put("teacherName",teacher.getName());
        }
        //查询分类名称
        Subject subjectOne = subjectService.getById(course.getSubjectParentId());
        if(subjectOne != null){
            course.getParam().put("subjectParentTitle",subjectOne.getTitle());
        }
        Subject subjectTwo = subjectService.getById(course.getSubjectId());
        if(subjectTwo != null){
            course.getParam().put("subjectTitle",subjectTwo.getTitle());
        }
        return course;
    }

    //增加课程基本信息
    // 重写保存课程信息的方法
    @Override
    public Long saveCourseInfo(CourseFormVo courseFormVo) {
        // 创建一个对象用于保存课程基本信息
        Course course = new Course();
        // 使用Spring的BeanUtils工具类，将courseFormVo对象的属性复制到course对象中，实现对象属性的拷贝
        BeanUtils.copyProperties(courseFormVo, course);
        // 将保存有课程基本信息的course对象插入数据库
        baseMapper.insert(course);
        // 创建一个对象用于保存课程的详细描述信息
        CourseDescription courseDescription = new CourseDescription();
        // 从courseFormVo中获取课程的描述信息，设置到courseDescription对象中
        courseDescription.setDescription(courseFormVo.getDescription());
        // 设置courseDescription对象的courseId属性为保存的课程基本信息的ID
        courseDescription.setCourseId(course.getId());
        // 调用courseDescriptionService的save方法保存课程的详细描述信息
        courseDescriptionService.save(courseDescription);
        // 返回保存的课程的ID
        return course.getId();
    }
    //根据id获取课程信息    修改-获取id
    @Override
    public CourseFormVo getCourseFormVoById(Long id) {
        //从course表中取数据
        Course course = baseMapper.selectById(id);
        // 如果未找到对应 id 的课程，返回 null
        if (course == null) {
            return null;
        }
        // 使用 QueryWrapper 构建查询条件，查询 course_description 表中的数据
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("course_id",id);

        //从course_description表中取数据
        CourseDescription courseDescription = courseDescriptionService.getOne(wrapper);
        //创建courseInfoForm对象
        CourseFormVo courseFormVo = new CourseFormVo();
        BeanUtils.copyProperties(course,courseFormVo);
        // 如果存在课程详情信息，将其描述复制到 courseFormVo 的 description 属性中
        if(courseDescription != null){
            courseFormVo.setDescription(courseDescription.getDescription());
        }
        return courseFormVo;
    }
    //根据id修改课程信息    修改-修改信息
    @Override
    public void updateCourseId(CourseFormVo courseFormVo) {
        //修改课程基本信息
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo,course);
        baseMapper.updateById(course);
        //修改课程详情信息
        CourseDescription description = new CourseDescription();

        QueryWrapper<CourseDescription> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",course.getId());

        description.setDescription(courseFormVo.getDescription());
//        description.setId(course.getId());
        courseDescriptionService.update(description,wrapper);
    }
    //步骤三-最终发布      根据id获取课程最终发布
    @Override
    public CoursePublishVo getCoursePublishVo(Long id) {
        return baseMapper.selectCoursePublishVoById(id);
    }
    //步骤三-最终发布  根据id发布课程
    @Override
    public boolean publishCourseById(Long id) {
        Course course = new Course();
        course.setId(id);
        course.setPublishTime(new Date());
        course.setStatus(1);
        return this.updateById(course);
    }

    //删除视频
    @Override
    public void removeCourseById(Long id) {
        //根据课程id删除小节
        videoService.removeVideoByCourseId(id);

        //根据课程id删除章节
        chapterService.removeChapterByCourseId(id);

        //根据课程id删除课程描述
        courseDescriptionService.removeById(id);

        //根据课程id删除课程
        baseMapper.deleteById(id);
    }

}
