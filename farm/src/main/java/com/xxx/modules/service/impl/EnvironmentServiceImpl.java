package com.xxx.modules.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.modules.mapper.EnvironmentMapper;
import com.xxx.modules.entity.Environment;
import com.xxx.modules.service.EnvironmentService;
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
 * 环境
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2025-02-24
 */
@Service
public class EnvironmentServiceImpl extends ServiceImpl<EnvironmentMapper, Environment> implements EnvironmentService {

    @Autowired
    private EnvironmentMapper environmentMapper;



    /**
     *  获取所有环境接口实现类
     * @param pageNum
     * @param pageSize
     * @param environment
     * @return
     */
    @Override
    public Result<?> selectEnvironmentList(Environment environment, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Environment> data = environmentMapper.selectListInfo(environment);
       return ResultUtil.success(1,"成功",new PageInfo<>(data));
    }


    /**
     * 获取单个环境接口实现类
     * @param id
     * @return
     */
    @Override
    public Result<?> selectEnvironmentInfo(Integer id) {
        Environment environment = environmentMapper.selectById(id);
        return ResultUtil.success(1,"成功",environment);
    }

    /**
     * 保存环境接口实现类
     * @param environment
     * @return
     */
    @Override
    public Result<?> saveEnvironmentInfo(Environment environment) {
        environment.setStatus(1);
        environment.setCreateTime(TimeUtil.getCurrentTime());
        environment.setUpdateTime(TimeUtil.getCurrentTime());
        environmentMapper.insert(environment);
        return ResultUtil.success(1,"成功",null);
}

    /**
     * 更新环境接口实现类
     * @param environment
     * @return
     */
    @Override
    public Result<?> updateEnvironmentInfo(Environment environment) {
        environment.setUpdateTime(TimeUtil.getCurrentTime());
        environmentMapper.updateById(environment);
        return ResultUtil.success(1,"成功",null);
    }

    /**
     * 根据id删除环境删除接口实现类
     * @param id
     * @return
     */
    @Override
    public Result<?> delEnvironmentInfo(Integer id) {
        environmentMapper.deleteById(id);
        return ResultUtil.success(1,"成功",null);
    }

    /**
     * 根据id集合批量删除环境接口实现类
     * @param idList
     * @return
     */
    @Override
    public Result<?> delBatchEnvironmentInfo(String idList) {
        List<String> list = Arrays.asList(idList.split(","));
        environmentMapper.deleteBatchIds(list);
        return ResultUtil.success(1,"成功",null);
    }

    /**
     * 导出
     * @param environment
     * @return
     */
    @Override
    public List<Environment> selectExcel(Environment environment) {
        return environmentMapper.selectListInfo(environment);
    }




}