import request from '../utils/request';


/**
 * 获取所有环境,包括条件查询接口
 * @param query
 * @returns {AxiosPromise}
 */
export const getEnvironmentList = query =>{
    return request({
        url:'/common/environment/getEnvironmentList',
        method:'get',
        params:query
    })
};

/**
 * 获取单个环境
 * @param query
 * @returns {AxiosPromise}
 */
export const getEnvironmentInfo = query =>{
    return request({
        url:'/common/environment/getEnvironmentInfo',
        method:'get',
        params:query
    })
};

/**
 * 保存环境接口
 * @param data
 * @returns {AxiosPromise}
 */
export const saveEnvironmentInfo = data =>{
    return request({
        url:'/common/environment/saveEnvironmentInfo',
        method:'post',
        data
    })
};
/**
 * 更新环境接口
 * @param data
 * @returns {AxiosPromise}
 */
export const updateEnvironmentInfo = data =>{
    return request({
        url:'/common/environment/updateEnvironmentInfo',
        method:'put',
        data
    })
};
/**
 *根据id删除环境接口
 * @param id
 * @returns {AxiosPromise}
 */
export const delEnvironmentInfo = id =>{
    return request({
        url:'/common/environment/delEnvironmentInfo',
        method:'delete',
        params:{id:id}
    })
};
/**
 * 根据id集合批量删除环境接口
 * @param id
 * @returns {AxiosPromise}
 */
export const delBatchEnvironmentInfo = id =>{
    return request({
        url:'/common/environment/delBatchEnvironmentInfo',
        method:'delete',
        params:{idList:id}
    })
};

/**
 * 导出excel
 */
export const exportEnvironmentExcel = query => {
    return request({
        url: '/common/environment/exportEnvironmentExcel',
        method: 'post',
        params: query,
        responseType: 'blob',
        header: {}
    });
};

/**
 * 导入excel
 */
export const uploadEnvironmentInfo = query => {
    return request({
        url: '/common/environment/importEnvironmentExcel',
        method: 'post',
        data: query,
        header: { 'Content-Type': 'multipart/form-data' }
    });
};


