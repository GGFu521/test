package com.xxx.modules.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxx.modules.entity.Environment;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
/**
 * 环境
 *
 */
@Mapper
public interface EnvironmentMapper extends BaseMapper<Environment> {

    List<Environment> selectListInfo(Environment environment);
}