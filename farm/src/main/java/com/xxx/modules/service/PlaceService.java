package com.xxx.modules.service;
import com.github.pagehelper.PageInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.modules.utils.Result;
import com.xxx.modules.entity.Place;
import java.util.List;
/**
 * 农场
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2025-02-24
 */
public interface PlaceService extends IService<Place>{

    /**
     *  获取所有农场接口
     * @param pageNum
     * @param pageSize
     * @param place
     * @return
     */
    Result <?>selectPlaceList(Place place, Integer pageNum, Integer pageSize);


    /**
     *  获取单个农场接口
     * @param id
     * @return
     */
    Result<?> selectPlaceInfo(Integer id);

    /**
     * 保存农场接口
     * @param place
     * @return
     */
    Result<?> savePlaceInfo(Place place);

    /**
     * 更新农场接口
     * @param place
     * @return
     */
    Result<?> updatePlaceInfo(Place place);

    /**
     * 根据id删除农场接口
     * @param id
     * @return
     */
    Result<?> delPlaceInfo(Integer id);

    /**
     * 根据id集合批量删除农场接口
     * @param idList
     * @return
     */
    Result<?> delBatchPlaceInfo(String idList);



        /**
     * 导出
     * @param place
     * @return
     */
    List<Place> selectExcel(Place place);
    
    

}