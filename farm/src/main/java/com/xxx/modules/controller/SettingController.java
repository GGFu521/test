package com.xxx.modules.controller;;
import com.xxx.modules.service.SettingService;
import com.xxx.modules.entity.Setting;
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
/**
 * 设置
 *
 */
@RestController
@RequestMapping("/common/setting")
@Api(tags="设置")
public class SettingController {
    @Autowired
    private SettingService settingService;

    /**
    *  获取所有设置
    * @param pageNum
    * @param pageSize
    * @param setting
    * @return
    */

    @GetMapping("/getSettingList")
    @ApiOperation("获取所有设置")
    public Result<?> getSettingList(Setting setting, @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
                                      @RequestParam(value="pageSize",defaultValue = "10") Integer pageSize){
        return settingService.selectSettingList(setting,pageNum,pageSize);
    }


    @GetMapping("/getSettingInfo")
    @ApiOperation("根据id获取单个设置")
    public Result<?> getSettingInfo(Integer id){
        return settingService.selectSettingInfo(id);
    }

    /**
    * 保存设置
    * @param setting
    * @return
    */
    @PostMapping("/saveSettingInfo")
    @ApiOperation("保存设置")
    public Result<?> saveSettingInfo(@RequestBody Setting setting){
        return settingService.saveSettingInfo(setting);
    }


    /**
     * 更新设置
     * @param setting
     * @return
     */

    @PutMapping("/updateSettingInfo")
    @ApiOperation("更新设置")
    public Result<?> updateSettingInfo(@RequestBody Setting setting){
        return settingService.updateSettingInfo(setting);
    }

    /**
     * 根据id删除设置
     * @param id
     * @return
     */
    @DeleteMapping("/delSettingInfo")
    @ApiOperation("根据id删除设置")
    public Result<?> delSettingInfo(Integer id){
        return settingService.delSettingInfo(id);
    }

    /**
     * 根据id集合批量删除设置
     * @param idList
     * @return
     */
    @DeleteMapping("/delBatchSettingInfo")
    @ApiOperation("根据id集合批量删除设置")
    public Result<?> delBatchSettingInfo(String idList){
        return settingService.delBatchSettingInfo(idList);
    }








}