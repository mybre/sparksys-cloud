package com.sparksys.activiti.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import com.sparksys.database.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * description: 流程控制实体类
 *
 * @author: zhouxinlei
 * @date: 2020-07-16 18:35:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("act_ru_task_control")
@ApiModel(value = "ActRuTaskControl对象", description = "")
public class ActRuTaskControl extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "上级id")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty(value = "任务id")
    @TableField("task_id")
    private String taskId;

    @ApiModelProperty(value = "任务定义key")
    @TableField("task_def_key")
    private String taskDefKey;

    @ApiModelProperty(value = "流程定义类型：default，first，claim")
    @TableField("task_def_type")
    private String taskDefType;

    @ApiModelProperty(value = "流程类型")
    @TableField("act_type")
    private Integer actType;


}
