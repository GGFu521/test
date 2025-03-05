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
@TableName("`environment`")
@ApiModel(value="environment对象", description="环境")
public class Environment {

    /**
     * 主键id
     */
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(value = "主键id")
	private Integer id;
    /**
     * 农场
     */
	@ApiModelProperty(value = "农场")
	private Integer placeId;
    /**
     * 温度/°C
     */
    @Excel(name = "温度/°C",width = 30)
	@ApiModelProperty(value = "温度/°C")
	private Double temValue;
    /**
     * 湿度/%
     */
    @Excel(name = "湿度/%",width = 30)
	@ApiModelProperty(value = "湿度/%")
	private Double humValue;
    /**
     * 光强/lux
     */
    @Excel(name = "光强/lux",width = 30)
	@ApiModelProperty(value = "光强/lux")
	private Double lightValue;
    /**
     * 状态
     */
    @Excel(name = "状态", replace = {"正常_1","异常_2",},width = 20)
	@ApiModelProperty(value = "状态")
	private Integer status;
    /**
     * 报警信息
     */
    @Excel(name = "报警信息",width = 30)
	@ApiModelProperty(value = "报警信息")
	private String alarmInfo;
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
	/**
	 * 农场
	 */
	@TableField(exist = false)
	@ApiModelProperty(value = "农场")
	@Excel(name = "农场",width=30)
	private String placeName;
	/**
	 * 地点
	 */
	@TableField(exist = false)
	@ApiModelProperty(value = "地点")
	@Excel(name = "地点",width=30)
	private String location;

}
