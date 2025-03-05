import request from '../utils/request';


/**
 * 获取所有农场,包括条件查询接口
 * @param query
 * @returns {AxiosPromise}
 */
export const getPlaceList = query =>{
    return request({
        url:'/common/place/getPlaceList',
        method:'get',
        params:query
    })
};

/**
 * 获取单个农场
 * @param query
 * @returns {AxiosPromise}
 */
export const getPlaceInfo = query =>{
    return request({
        url:'/common/place/getPlaceInfo',
        method:'get',
        params:query
    })
};

/**
 * 保存农场接口
 * @param data
 * @returns {AxiosPromise}
 */
export const savePlaceInfo = data =>{
    return request({
        url:'/common/place/savePlaceInfo',
        method:'post',
        data
    })
};
/**
 * 更新农场接口
 * @param data
 * @returns {AxiosPromise}
 */
export const updatePlaceInfo = data =>{
    return request({
        url:'/common/place/updatePlaceInfo',
        method:'put',
        data
    })
};
/**
 *根据id删除农场接口
 * @param id
 * @returns {AxiosPromise}
 */
export const delPlaceInfo = id =>{
    return request({
        url:'/common/place/delPlaceInfo',
        method:'delete',
        params:{id:id}
    })
};
/**
 * 根据id集合批量删除农场接口
 * @param id
 * @returns {AxiosPromise}
 */
export const delBatchPlaceInfo = id =>{
    return request({
        url:'/common/place/delBatchPlaceInfo',
        method:'delete',
        params:{idList:id}
    })
};

/**
 * 导出excel
 */
export const exportPlaceExcel = query => {
    return request({
        url: '/common/place/exportPlaceExcel',
        method: 'post',
        params: query,
        responseType: 'blob',
        header: {}
    });
};

/**
 * 导入excel
 */
export const uploadPlaceInfo = query => {
    return request({
        url: '/common/place/importPlaceExcel',
        method: 'post',
        data: query,
        header: { 'Content-Type': 'multipart/form-data' }
    });
};


