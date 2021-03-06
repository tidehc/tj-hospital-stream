package com.hxqh.enums;

import com.hxqh.constant.Constant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * 需确认报警等级并增加至 {@link  Constant#ALARM_MAP}
 *
 * Created by Ocean lin on 2020/4/16.
 *
 * @author Ocean lin
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum OtherAlarmLevel implements AlarmLevel {

    // todo 中压开关

    // 变压器
    FanOperationStatus("FanOperationStatus","风机运行报警");

    // todo 低压设备



    private String code;

    private String message;
}
