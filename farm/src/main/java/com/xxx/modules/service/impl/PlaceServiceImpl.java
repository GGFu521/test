package com.xxx.modules.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.modules.mapper.PlaceMapper;
import com.xxx.modules.entity.Place;
import com.xxx.modules.service.PlaceService;
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
 * 农场
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2025-02-24
 */
@Service
public class PlaceServiceImpl extends ServiceImpl<PlaceMapper, Place> implements PlaceService {

    @Autowired
    private PlaceMapper placeMapper;



    /**
     *  获取所有农场接口实现类
     * @param pageNum
     * @param pageSize
     * @param place
     * @return
     */
    @Override
    public Result<?> selectPlaceList(Place place, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<Place> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        if (place.getPlaceName() !=null){
            wrapper.like("place_name",place.getPlaceName());
        }
        if (place.getLocation() !=null){
            wrapper.like("location",place.getLocation());
        }
        List<Place> data = placeMapper.selectList(wrapper);
        return ResultUtil.success(1,"成功",new PageInfo<>(data));
    }


    /**
     * 获取单个农场接口实现类
     * @param id
     * @return
     */
    @Override
    public Result<?> selectPlaceInfo(Integer id) {
        Place place = placeMapper.selectById(id);
        return ResultUtil.success(1,"成功",place);
    }

    /**
     * 保存农场接口实现类
     * @param place
     * @return
     */
    @Override
    public Result<?> savePlaceInfo(Place place) {
        place.setCreateTime(TimeUtil.getCurrentTime());
        place.setUpdateTime(TimeUtil.getCurrentTime());
        placeMapper.insert(place);
        return ResultUtil.success(1,"成功",null);
}

    /**
     * 更新农场接口实现类
     * @param place
     * @return
     */
    @Override
    public Result<?> updatePlaceInfo(Place place) {
        place.setUpdateTime(TimeUtil.getCurrentTime());
        placeMapper.updateById(place);
        return ResultUtil.success(1,"成功",null);
    }

    /**
     * 根据id删除农场删除接口实现类
     * @param id
     * @return
     */
    @Override
    public Result<?> delPlaceInfo(Integer id) {
        placeMapper.deleteById(id);
        return ResultUtil.success(1,"成功",null);
    }

    /**
     * 根据id集合批量删除农场接口实现类
     * @param idList
     * @return
     */
    @Override
    public Result<?> delBatchPlaceInfo(String idList) {
        List<String> list = Arrays.asList(idList.split(","));
        placeMapper.deleteBatchIds(list);
        return ResultUtil.success(1,"成功",null);
    }

    /**
     * 导出
     * @param place
     * @return
     */
    @Override
    public List<Place> selectExcel(Place place) {
        QueryWrapper<Place> wrapper = new QueryWrapper<>();
        if (place.getPlaceName() !=null){
            wrapper.like("place_name",place.getPlaceName());
        }
        if (place.getLocation() !=null){
            wrapper.like("location",place.getLocation());
        }
        return  placeMapper.selectList(wrapper);
    }




}