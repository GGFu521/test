package com.xxx.modules.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxx.modules.entity.Setting;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
/**
 * 设置
 *
 */
@Mapper
public interface SettingMapper extends BaseMapper<Setting> {

}