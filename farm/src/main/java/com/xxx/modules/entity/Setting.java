package com.xxx.modules.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@Data
@TableName("`setting`")
@ApiModel(value="setting对象", description="设置")
public class Setting {

    /**
     * 主键id
     */
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(value = "主键id")
	private Integer id;
    /**
     * 温度最小值
     */
	@ApiModelProperty(value = "温度最小值")
	private Double minTemValue;
    /**
     * 温度最大值
     */
	@ApiModelProperty(value = "温度最大值")
	private Double maxTemValue;
    /**
     * 湿度最小值
     */
	@ApiModelProperty(value = "湿度最小值")
	private Double minHumValue;
    /**
     * 湿度最大值
     */
	@ApiModelProperty(value = "湿度最大值")
	private Double maxHumValue;
    /**
     * 最小光强
     */
	@ApiModelProperty(value = "最小光强")
	private Double minLightValue;
    /**
     * 最大光强
     */
	@ApiModelProperty(value = "最大光强")
	private Double maxLightValue;
    /**
     * 创建时间
     */
	@ApiModelProperty(value = "创建时间")
	private String createTime;
    /**
     * 更新时间
     */
	@ApiModelProperty(value = "更新时间")
	private String updateTime;

}
