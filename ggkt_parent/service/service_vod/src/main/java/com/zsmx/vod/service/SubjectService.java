package com.zsmx.vod.service;

import com.zsmx.model.vod.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author zsmx
 * @since 2024-01-06
 */
public interface SubjectService extends IService<Subject> {

    List<Subject> selectList(Long id);

    void exportData(HttpServletResponse response);

    void importData(MultipartFile file);
}
