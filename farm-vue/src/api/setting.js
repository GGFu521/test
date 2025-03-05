import request from '../utils/request';


/**
 * 获取所有设置,包括条件查询接口
 * @param query
 * @returns {AxiosPromise}
 */
export const getSettingList = query =>{
    return request({
        url:'/common/setting/getSettingList',
        method:'get',
        params:query
    })
};

/**
 * 获取单个设置
 * @param query
 * @returns {AxiosPromise}
 */
export const getSettingInfo = query =>{
    return request({
        url:'/common/setting/getSettingInfo',
        method:'get',
        params:query
    })
};

/**
 * 保存设置接口
 * @param data
 * @returns {AxiosPromise}
 */
export const saveSettingInfo = data =>{
    return request({
        url:'/common/setting/saveSettingInfo',
        method:'post',
        data
    })
};
/**
 * 更新设置接口
 * @param data
 * @returns {AxiosPromise}
 */
export const updateSettingInfo = data =>{
    return request({
        url:'/common/setting/updateSettingInfo',
        method:'put',
        data
    })
};
/**
 *根据id删除设置接口
 * @param id
 * @returns {AxiosPromise}
 */
export const delSettingInfo = id =>{
    return request({
        url:'/common/setting/delSettingInfo',
        method:'delete',
        params:{id:id}
    })
};
/**
 * 根据id集合批量删除设置接口
 * @param id
 * @returns {AxiosPromise}
 */
export const delBatchSettingInfo = id =>{
    return request({
        url:'/common/setting/delBatchSettingInfo',
        method:'delete',
        params:{idList:id}
    })
};



