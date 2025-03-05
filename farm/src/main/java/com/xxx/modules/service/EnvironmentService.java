package com.xxx.modules.service;
import com.github.pagehelper.PageInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.modules.utils.Result;
import com.xxx.modules.entity.Environment;
import java.util.List;
/**
 * 环境
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2025-02-24
 */
public interface EnvironmentService extends IService<Environment>{

    /**
     *  获取所有环境接口
     * @param pageNum
     * @param pageSize
     * @param environment
     * @return
     */
    Result <?>selectEnvironmentList(Environment environment, Integer pageNum, Integer pageSize);


    /**
     *  获取单个环境接口
     * @param id
     * @return
     */
    Result<?> selectEnvironmentInfo(Integer id);

    /**
     * 保存环境接口
     * @param environment
     * @return
     */
    Result<?> saveEnvironmentInfo(Environment environment);

    /**
     * 更新环境接口
     * @param environment
     * @return
     */
    Result<?> updateEnvironmentInfo(Environment environment);

    /**
     * 根据id删除环境接口
     * @param id
     * @return
     */
    Result<?> delEnvironmentInfo(Integer id);

    /**
     * 根据id集合批量删除环境接口
     * @param idList
     * @return
     */
    Result<?> delBatchEnvironmentInfo(String idList);



        /**
     * 导出
     * @param environment
     * @return
     */
    List<Environment> selectExcel(Environment environment);
    
    

}