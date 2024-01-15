package com.zsmx.vod.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsmx.model.vod.Subject;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 课程科目 Mapper 接口
 * </p>
 *
 * @author zsmx
 * @since 2024-01-06
 */
@Repository
public interface SubjectMapper extends BaseMapper<Subject> {

}
