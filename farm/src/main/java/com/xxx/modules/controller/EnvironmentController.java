package com.xxx.modules.controller;;
import com.xxx.modules.service.EnvironmentService;
import com.xxx.modules.entity.Environment;
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
 * 环境
 *
 */
@RestController
@RequestMapping("/common/environment")
@Api(tags="环境")
public class EnvironmentController {
    @Autowired
    private EnvironmentService environmentService;

    /**
    *  获取所有环境
    * @param pageNum
    * @param pageSize
    * @param environment
    * @return
    */

    @GetMapping("/getEnvironmentList")
    @ApiOperation("获取所有环境")
    public Result<?> getEnvironmentList(Environment environment, @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
                                      @RequestParam(value="pageSize",defaultValue = "10") Integer pageSize){
        return environmentService.selectEnvironmentList(environment,pageNum,pageSize);
    }


    @GetMapping("/getEnvironmentInfo")
    @ApiOperation("根据id获取单个环境")
    public Result<?> getEnvironmentInfo(Integer id){
        return environmentService.selectEnvironmentInfo(id);
    }

    /**
    * 保存环境
    * @param environment
    * @return
    */
    @PostMapping("/saveEnvironmentInfo")
    @ApiOperation("保存环境")
    public Result<?> saveEnvironmentInfo(@RequestBody Environment environment){
        return environmentService.saveEnvironmentInfo(environment);
    }


    /**
     * 更新环境
     * @param environment
     * @return
     */

    @PutMapping("/updateEnvironmentInfo")
    @ApiOperation("更新环境")
    public Result<?> updateEnvironmentInfo(@RequestBody Environment environment){
        return environmentService.updateEnvironmentInfo(environment);
    }

    /**
     * 根据id删除环境
     * @param id
     * @return
     */
    @DeleteMapping("/delEnvironmentInfo")
    @ApiOperation("根据id删除环境")
    public Result<?> delEnvironmentInfo(Integer id){
        return environmentService.delEnvironmentInfo(id);
    }

    /**
     * 根据id集合批量删除环境
     * @param idList
     * @return
     */
    @DeleteMapping("/delBatchEnvironmentInfo")
    @ApiOperation("根据id集合批量删除环境")
    public Result<?> delBatchEnvironmentInfo(String idList){
        return environmentService.delBatchEnvironmentInfo(idList);
    }





        /**
         * 导出
         * @param environment
         * @return
         */
    @PostMapping("/exportEnvironmentExcel")
    public void export(HttpServletResponse response, Environment environment) {
        try{
            System.out.println("开始导出");
            List<Environment> data = environmentService.selectExcel(environment);
            EasyPoiUtil.exportExcel(data, "环境表", "导出sheet1",Environment.class, "环境信息表.xlsx", response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }




}