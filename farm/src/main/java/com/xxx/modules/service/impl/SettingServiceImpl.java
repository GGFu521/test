package com.xxx.modules.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.modules.mapper.SettingMapper;
import com.xxx.modules.entity.Setting;
import com.xxx.modules.service.SettingService;
import com.xxx.modules.entity.User;
import com.xxx.modules.mapper.UserMapper;
import com.xxx.modules.utils.Result;
import com.xxx.modules.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import com.xxx.modules.utils.TimeUtil;

/**
 * 设置
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2025-02-24
 */
@Service
public class SettingServiceImpl extends ServiceImpl<SettingMapper, Setting> implements SettingService {

    @Autowired
    private SettingMapper settingMapper;



    /**
     *  获取所有设置接口实现类
     * @param pageNum
     * @param pageSize
     * @param setting
     * @return
     */
    @Override
    public Result<?> selectSettingList(Setting setting, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<Setting> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        List<Setting> data = settingMapper.selectList(wrapper);
        return ResultUtil.success(1,"成功",new PageInfo<>(data));
    }


    /**
     * 获取单个设置接口实现类
     * @param id
     * @return
     */
    @Override
    public Result<?> selectSettingInfo(Integer id) {
        Setting setting = settingMapper.selectById(id);
        return ResultUtil.success(1,"成功",setting);
    }

    /**
     * 保存设置接口实现类
     * @param setting
     * @return
     */
    @Override
    public Result<?> saveSettingInfo(Setting setting) {
        setting.setCreateTime(TimeUtil.getCurrentTime());
        setting.setUpdateTime(TimeUtil.getCurrentTime());
        settingMapper.insert(setting);
        return ResultUtil.success(1,"成功",null);
}

    /**
     * 更新设置接口实现类
     * @param setting
     * @return
     */
    @Override
    public Result<?> updateSettingInfo(Setting setting) {
        setting.setUpdateTime(TimeUtil.getCurrentTime());
        settingMapper.updateById(setting);
        return ResultUtil.success(1,"成功",null);
    }

    /**
     * 根据id删除设置删除接口实现类
     * @param id
     * @return
     */
    @Override
    public Result<?> delSettingInfo(Integer id) {
        settingMapper.deleteById(id);
        return ResultUtil.success(1,"成功",null);
    }

    /**
     * 根据id集合批量删除设置接口实现类
     * @param idList
     * @return
     */
    @Override
    public Result<?> delBatchSettingInfo(String idList) {
        List<String> list = Arrays.asList(idList.split(","));
        settingMapper.deleteBatchIds(list);
        return ResultUtil.success(1,"成功",null);
    }

}