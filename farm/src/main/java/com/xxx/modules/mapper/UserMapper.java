package com.xxx.modules.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxx.modules.entity.User;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
/**
 * 用户
 *
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

	
}