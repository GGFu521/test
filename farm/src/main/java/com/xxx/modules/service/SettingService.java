package com.xxx.modules.service;
import com.github.pagehelper.PageInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.modules.utils.Result;
import com.xxx.modules.entity.Setting;
import java.util.List;
/**
 * 设置
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2025-02-24
 */
public interface SettingService extends IService<Setting>{

    /**
     *  获取所有设置接口
     * @param pageNum
     * @param pageSize
     * @param setting
     * @return
     */
    Result <?>selectSettingList(Setting setting, Integer pageNum, Integer pageSize);


    /**
     *  获取单个设置接口
     * @param id
     * @return
     */
    Result<?> selectSettingInfo(Integer id);

    /**
     * 保存设置接口
     * @param setting
     * @return
     */
    Result<?> saveSettingInfo(Setting setting);

    /**
     * 更新设置接口
     * @param setting
     * @return
     */
    Result<?> updateSettingInfo(Setting setting);

    /**
     * 根据id删除设置接口
     * @param id
     * @return
     */
    Result<?> delSettingInfo(Integer id);

    /**
     * 根据id集合批量删除设置接口
     * @param idList
     * @return
     */
    Result<?> delBatchSettingInfo(String idList);




}