package com.xxx.modules.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@Data
@TableName("`place`")
@ApiModel(value="place对象", description="农场")
public class Place {

    /**
     * 主键id
     */
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(value = "主键id")
	private Integer id;
    /**
     * 农场名
     */
    @Excel(name = "农场名",width = 30)
	@ApiModelProperty(value = "农场名")
	private String placeName;
    /**
     * 地点
     */
    @Excel(name = "地点",width = 30)
	@ApiModelProperty(value = "地点")
	private String location;
    /**
     * 创建时间
     */
    @Excel(name = "创建时间",width = 30)
	@ApiModelProperty(value = "创建时间")
	private String createTime;
    /**
     * 更新时间
     */
    @Excel(name = "更新时间",width = 30)
	@ApiModelProperty(value = "更新时间")
	private String updateTime;

}
