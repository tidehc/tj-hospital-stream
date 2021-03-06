package com.hxqh.enums;

import com.hxqh.constant.Constant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 需确认报警等级并增加至 {@link  Constant#ALARM_MAP}
 * <p>
 * Created by Ocean lin on 2020/3/11.
 *
 * @author Ocean lin
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum FirstAlarmLevel implements AlarmLevel {

    // 中压开关柜
    QuickBreak("QuickBreak", "速断"),

    OverCurrent("OverCurrent", "过流"),

    OverCurrentDelay("OverCurrentDelay", "延时过流"),


    // 变压器
    WindingOvertemperatureTrip("WindingOvertemperatureTrip", "超温跳闸"),


    // todo 未提供
    noinfo("0", "差动保护");

    private String code;

    private String message;


}
