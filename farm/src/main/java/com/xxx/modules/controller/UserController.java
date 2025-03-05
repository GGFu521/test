package com.xxx.modules.controller;;
import com.xxx.modules.service.UserService;
import com.xxx.modules.entity.User;
import com.xxx.modules.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
 * 用户
 *
 */
@RestController
@RequestMapping("/common/user")
@Api(tags="用户")
public class UserController {
    @Autowired
    private UserService userService;

    /**
    *  获取所有用户
    * @param pageNum
    * @param pageSize
    * @param user
    * @return
    */

    @GetMapping("/getUserList")
    @ApiOperation("获取所有用户")
    public Result getUserList(User user, @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
                                      @RequestParam(value="pageSize",defaultValue = "10") Integer pageSize){
        return userService.selectUserList(user,pageNum,pageSize);
    }


    @GetMapping("/getUserInfo")
    @ApiOperation("根据id获取单个用户")
    public Result getUserInfo(Integer id){
        return userService.selectUserInfo(id);
    }

    /**
    * 保存用户
    * @param user
    * @return
    */
    @PostMapping("/saveUserInfo")
    @ApiOperation("保存用户")
    public Result saveUserInfo(@RequestBody User user){
        return userService.saveUserInfo(user);
    }


    /**
     * 更新用户
     * @param user
     * @return
     */

    @PutMapping("/updateUserInfo")
    @ApiOperation("更新用户")
    public Result updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/delUserInfo")
    @ApiOperation("根据id删除用户")
    public Result delUserInfo(Integer id){
        return userService.delUserInfo(id);
    }

    /**
     * 根据id集合批量删除用户
     * @param idList
     * @return
     */
    @DeleteMapping("/delBatchUserInfo")
    @ApiOperation("根据id集合批量删除用户")
    public Result delBatchUserInfo(String idList){
        return userService.delBatchUserInfo(idList);
    }

    /**
     * 登录接口(根据用户名和密码)
     * @param user
     * @return
     */

    @PostMapping("/login")
    @ApiOperation("用户登录接口")
    public Result login(@RequestBody User user){
        return userService.login(user);
    }



    /**
    *
    * @param user
    * @return
     */
    @PostMapping("/updatePassword")
    @ApiOperation("用户更新密码接口")
    public Result updatePassword(@RequestBody User user){
       return userService.updatePassword(user);
    }






    @PostMapping("/createUserData")
    public Result createUserData(){

        List<String> imageList = new ArrayList<>();
        imageList.add("https://gulimall-psw.oss-cn-hangzhou.aliyuncs.com/2023-10-25//242357c9-08c4-4c4f-854d-67de9b271dbe_u=1996284881,1109846881&fm=253&fmt=auto&app=138&f=JPEG.webp");
        imageList.add("https://gulimall-psw.oss-cn-hangzhou.aliyuncs.com/2023-10-30//0d09b8e2-e66d-4231-8d35-5c4552a48f2a_2dd8fe9b59774c7ca160bb2d5741c763.jpg");
        imageList.add("https://gulimall-psw.oss-cn-hangzhou.aliyuncs.com/2023-11-02//d5f5276d-07bb-435c-8b83-6673fed5f22d_Snipaste_2023-03-13_19-49-26.png");
        imageList.add("https://gulimall-psw.oss-cn-hangzhou.aliyuncs.com/2022-12-13//22400dd3-cc8e-4477-823b-b3fb698ef9d5_25.png");
        imageList.add("http://yjy-xiaotuxian-dev.oss-cn-beijing.aliyuncs.com/picture/2021-04-05/7f483771-6831-4a7a-abdb-2625acb755f3.png");
        imageList.add("https://gulimall-psw.oss-cn-hangzhou.aliyuncs.com/2023-10-30//0d09b8e2-e66d-4231-8d35-5c4552a48f2a_2dd8fe9b59774c7ca160bb2d5741c763.jpg");
        imageList.add("https://gulimall-psw.oss-cn-hangzhou.aliyuncs.com/2023-11-02//d5f5276d-07bb-435c-8b83-6673fed5f22d_Snipaste_2023-03-13_19-49-26.png");
        imageList.add("https://gulimall-psw.oss-cn-hangzhou.aliyuncs.com/2023-10-25//5aeb86e4-5c96-433a-8180-2ef50443dd5c_d0176b163973961ed01818d04ee94a06 (1).png");
        imageList.add("https://gulimall-psw.oss-cn-hangzhou.aliyuncs.com/2023-10-25//7f50ce5d-04ab-4db6-88a5-c721e0256256_7f6a7b20-7902-4b43-b9c5-f33151ef1334.jpg");
        imageList.add("https://gulimall-psw.oss-cn-hangzhou.aliyuncs.com/2021-05-04//0bed6c30-d19f-4771-8fcd-7de722195643_2016121809101738-480x300.jpg");
        for (int i=1;i<11;i++) {
            User user = new User();
            user.setCreateTime(TimeUtil.getCurrentTime());
            user.setUpdateTime(TimeUtil.getCurrentTime());
            user.setImageUrl( imageList.get(i-1) );

            user.setEmail("电子邮件"+i);

            user.setUserName("用户名"+i);

            user.setPassword("密码"+i);

            user.setRealName("姓名"+i);

            user.setPhone("联系方式"+i);

            user.setAddress("地址"+i);

            user.setSex(1);

            user.setUserType(1);

            userService.saveUserInfo(user);
        }
        return ResultUtil.success(1,"生成成功",null);
    }
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/updateBatchTime")
    public Result updateBatchTime(){
        String s ="SELECT `table_name`,`table_comment` FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE()";
        List<Map<String, Object>> tableList = jdbcTemplate.queryForList(s);
        System.out.println(tableList);
        for (Map<String, Object> map:tableList){
            try {
                String table_name = String.valueOf(map.get("table_name"));
                String createStr = "UPDATE `"+table_name+"` set create_time = "+ "'"+TimeUtil.getCurrentTime()+"'";
                jdbcTemplate.execute(createStr);

                String updateStr = "UPDATE `"+table_name+"` set update_time = "+ "'"+TimeUtil.getCurrentTime()+"'";
                jdbcTemplate.execute(updateStr);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return ResultUtil.success(1,"正常",null);
    }

}