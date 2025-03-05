package com.xxx.modules.controller;;
import com.xxx.modules.service.PlaceService;
import com.xxx.modules.entity.Place;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import com.github.pagehelper.PageInfo;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.xxx.modules.utils.Result;
import com.xxx.modules.utils.ResultUtil;
import com.xxx.modules.utils.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
/**
 * 农场
 *
 */
@RestController
@RequestMapping("/common/place")
@Api(tags="农场")
public class PlaceController {
    @Autowired
    private PlaceService placeService;

    /**
    *  获取所有农场
    * @param pageNum
    * @param pageSize
    * @param place
    * @return
    */

    @GetMapping("/getPlaceList")
    @ApiOperation("获取所有农场")
    public Result<?> getPlaceList(Place place, @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
                                      @RequestParam(value="pageSize",defaultValue = "10") Integer pageSize){
        return placeService.selectPlaceList(place,pageNum,pageSize);
    }


    @GetMapping("/getPlaceInfo")
    @ApiOperation("根据id获取单个农场")
    public Result<?> getPlaceInfo(Integer id){
        return placeService.selectPlaceInfo(id);
    }

    /**
    * 保存农场
    * @param place
    * @return
    */
    @PostMapping("/savePlaceInfo")
    @ApiOperation("保存农场")
    public Result<?> savePlaceInfo(@RequestBody Place place){
        return placeService.savePlaceInfo(place);
    }


    /**
     * 更新农场
     * @param place
     * @return
     */

    @PutMapping("/updatePlaceInfo")
    @ApiOperation("更新农场")
    public Result<?> updatePlaceInfo(@RequestBody Place place){
        return placeService.updatePlaceInfo(place);
    }

    /**
     * 根据id删除农场
     * @param id
     * @return
     */
    @DeleteMapping("/delPlaceInfo")
    @ApiOperation("根据id删除农场")
    public Result<?> delPlaceInfo(Integer id){
        return placeService.delPlaceInfo(id);
    }

    /**
     * 根据id集合批量删除农场
     * @param idList
     * @return
     */
    @DeleteMapping("/delBatchPlaceInfo")
    @ApiOperation("根据id集合批量删除农场")
    public Result<?> delBatchPlaceInfo(String idList){
        return placeService.delBatchPlaceInfo(idList);
    }





        /**
         * 导出
         * @param place
         * @return
         */
    @PostMapping("/exportPlaceExcel")
    public void export(HttpServletResponse response, Place place) {
        try{
            System.out.println("开始导出");
            List<Place> data = placeService.selectExcel(place);
            EasyPoiUtil.exportExcel(data, "农场表", "导出sheet1",Place.class, "农场信息表.xlsx", response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }




}