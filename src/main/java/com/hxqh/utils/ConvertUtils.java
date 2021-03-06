package com.hxqh.utils;

import com.alibaba.fastjson.JSON;
import com.hxqh.domain.*;
import com.hxqh.domain.base.IEDEntity;
import com.hxqh.domain.base.IEDParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.types.Row;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Created by Ocean lin on 2020/2/18.
 *
 * @author Ocean lin
 */
@SuppressWarnings("Duplicates")
public class ConvertUtils {

    @Deprecated
    public static YcAts convert2YcAts(IEDEntity entity) {
        YcAts ycAts = new YcAts();
        ycAts.setIEDName(entity.getIEDName());
        ycAts.setColTime(entity.getColTime());
        ycAts.setAssetYpe(entity.getAssetYpe());
        ycAts.setProductModel(entity.getProductModel().trim());

        List<IEDParam> iedParam = entity.getIEDParam();
        Map<String, List<IEDParam>> parameterMap = iedParam.stream().collect(Collectors.groupingBy(IEDParam::getVariableName));

        Field[] declaredFields = ycAts.getClass().getDeclaredFields();

        for (Field field : declaredFields) {
            String attr = StringUtils.capitalize(field.getName());
            if (!"SerialVersionUID".equals(attr)) {
                try {
                    Method getMethod = ycAts.getClass().getDeclaredMethod("get" + attr);
                    Type genericReturnType = getMethod.getGenericReturnType();
                    if (genericReturnType.getTypeName().equals(Double.class.getName())) {
                        Method setMethod = ycAts.getClass().getDeclaredMethod("set" + attr, Double.class);
                        setMethod.invoke(ycAts, null == parameterMap.get(attr) ? 0 : parameterMap.get(attr).get(0).getValue());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ycAts;
    }


    @Deprecated
    public static YxAts convert2YxAts(IEDEntity entity) {
        YxAts yxAts = new YxAts();
        yxAts.setIEDName(entity.getIEDName());
        yxAts.setColTime(entity.getColTime());
        yxAts.setAssetYpe(entity.getAssetYpe());
        yxAts.setProductModel(entity.getProductModel().trim());
        yxAts.setVariableName(entity.getIEDParam().get(0).getVariableName());
        yxAts.setValue(entity.getIEDParam().get(0).getValue().intValue());
        return yxAts;
    }


    /**
     * IEDEntity转换成中压开关柜遥测
     *
     * @param entity
     * @return
     */
    public static YcMediumVoltage convert2YcMediumVoltage(IEDEntity entity) {
        YcMediumVoltage ycMediumVoltage = new YcMediumVoltage();
        ycMediumVoltage.setIEDName(entity.getIEDName());
        ycMediumVoltage.setColTime(entity.getColTime());
        ycMediumVoltage.setAssetYpe(entity.getAssetYpe());
        ycMediumVoltage.setProductModel(entity.getProductModel().trim());
        ycMediumVoltage.setLocation(entity.getLocation());
        ycMediumVoltage.setParent(entity.getParent());

        ycMediumVoltage.setProductModelB(entity.getProductModelB());
        ycMediumVoltage.setProductModelC(entity.getProductModelC());
        ycMediumVoltage.setFractionRatio(entity.getFractionRatio());
        ycMediumVoltage.setLoadRate(entity.getLoadRate());


        List<IEDParam> iedParam = entity.getIEDParam();
        Map<String, List<IEDParam>> parameterMap = iedParam.stream().collect(Collectors.groupingBy(IEDParam::getVariableName));

        Field[] declaredFields = ycMediumVoltage.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            String attr = StringUtils.capitalize(field.getName());
            if (!"SerialVersionUID".equals(attr)) {
                try {
                    Method getMethod = ycMediumVoltage.getClass().getDeclaredMethod("get" + attr);
                    Type genericReturnType = getMethod.getGenericReturnType();
                    if (genericReturnType.getTypeName().equals(Double.class.getName())) {
                        Method setMethod = ycMediumVoltage.getClass().getDeclaredMethod("set" + attr, Double.class);
                        setMethod.invoke(ycMediumVoltage, null == parameterMap.get(attr) ? 0.0 : parameterMap.get(attr).get(0).getValue());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ycMediumVoltage;
    }


    /**
     * Row转低压设备遥测
     *
     * @param row TRA2,YC,2020-04-13 14:26:03,变压器,A001,AH20,SCZB11-2500/35,2500kVA,35/0.4kV,0.0,0.0,[APhaseTemperature,90, BPhaseTemperature,40, CPhaseTemperature,50, DRoadTemperature,31]
     * @return
     */
    public static YcTransformer convert2YcTransformer(Row row) {
        YcTransformer ycTransformer = new YcTransformer();
        ycTransformer.setIEDName(row.getField(0).toString());
        ycTransformer.setColTime(DateUtils.formatDate(row.getField(2).toString()));
        ycTransformer.setAssetYpe(row.getField(3).toString());
        ycTransformer.setLocation(row.getField(4).toString());
        ycTransformer.setParent(row.getField(5).toString());
        ycTransformer.setProductModel(row.getField(6).toString());

        ycTransformer.setProductModelB(row.getField(7).toString());
        ycTransformer.setProductModelC(row.getField(8).toString());
        ycTransformer.setFractionRatio(Double.parseDouble(row.getField(9).toString()));
        ycTransformer.setLoadRate(Double.parseDouble(row.getField(10).toString()));

        Row[] rows = (Row[]) row.getField(11);
        List<IEDParam> iedParams = new ArrayList<>();
        if (rows.length > 0) {
            for (Row r : rows) {
                IEDParam param = new IEDParam(r.getField(0).toString(), Double.parseDouble(r.getField(1).toString()));
                iedParams.add(param);
            }
        }
        Map<String, List<IEDParam>> parameterMap = iedParams.stream().collect(Collectors.groupingBy(IEDParam::getVariableName));

        Field[] declaredFields = ycTransformer.getClass().getDeclaredFields();

        for (Field field : declaredFields) {
            String attr = StringUtils.capitalize(field.getName());
            if (!"SerialVersionUID".equals(attr)) {
                try {
                    Method getMethod = ycTransformer.getClass().getDeclaredMethod("get" + attr);
                    Type genericReturnType = getMethod.getGenericReturnType();
                    if (genericReturnType.getTypeName().equals(Double.class.getName())) {
                        Method setMethod = ycTransformer.getClass().getDeclaredMethod("set" + attr, Double.class);
                        setMethod.invoke(ycTransformer, null == parameterMap.get(attr) ? 0.0 : parameterMap.get(attr).get(0).getValue());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return ycTransformer;
    }


    /**
     * Row转低压设备遥测
     *
     * @param row 2AA1,YC,2020-04-14 16:50:03,低压开关设备-ACB,A001,TRA2,72E,2500kVA,TRA2,[PhaseL1CurrentPercent,89, PhaseL1L2Voltage,22, PhaseL2CurrentPercent,33, PhaseL2L3Voltage,44, PhaseL3CurrentPercent,88, PhaseL3L1Voltage,90, PositiveActive,555, PositiveReactive,888, PowerFactor,999, OperationNumber,234]
     * @return
     */
    public static YcLowPressure convert2YcLowPressure(Row row) {
        YcLowPressure ycLowPressure = new YcLowPressure();
        ycLowPressure.setIEDName(row.getField(0).toString());
        ycLowPressure.setColTime(DateUtils.formatDate(row.getField(2).toString()));
        ycLowPressure.setAssetYpe(row.getField(3).toString());
        ycLowPressure.setLocation(row.getField(4).toString());
        ycLowPressure.setParent(row.getField(5).toString());
        ycLowPressure.setProductModel(row.getField(6).toString());

        ycLowPressure.setProductModelB(row.getField(7).toString());
        ycLowPressure.setProductModelC(row.getField(8).toString());

        Row[] rows = (Row[]) row.getField(9);
        List<IEDParam> iedParams = new ArrayList<>();
        if (rows.length > 0) {
            for (Row r : rows) {
                IEDParam param = new IEDParam(r.getField(0).toString(), Double.parseDouble(r.getField(1).toString()));
                iedParams.add(param);
            }
        }
        Map<String, List<IEDParam>> parameterMap = iedParams.stream().collect(Collectors.groupingBy(IEDParam::getVariableName));

        Field[] declaredFields = ycLowPressure.getClass().getDeclaredFields();

        for (Field field : declaredFields) {
            String attr = StringUtils.capitalize(field.getName());
            if (!"SerialVersionUID".equals(attr)) {
                try {
                    Method getMethod = ycLowPressure.getClass().getDeclaredMethod("get" + attr);
                    Type genericReturnType = getMethod.getGenericReturnType();
                    if (genericReturnType.getTypeName().equals(Double.class.getName())) {
                        Method setMethod = ycLowPressure.getClass().getDeclaredMethod("set" + attr, Double.class);
                        setMethod.invoke(ycLowPressure, null == parameterMap.get(attr) ? 0.0 : parameterMap.get(attr).get(0).getValue());
                    }
                    if (genericReturnType.getTypeName().equals(Integer.class.getName())) {
                        Method setMethod = ycLowPressure.getClass().getDeclaredMethod("set" + attr, Integer.class);
                        setMethod.invoke(ycLowPressure, null == parameterMap.get(attr) ? 0 : parameterMap.get(attr).get(0).getValue().intValue());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ycLowPressure;
    }


    /**
     * 对象属性转换成Map
     *
     * @param object
     * @return
     * @throws IllegalAccessException
     */
    public static TreeMap<String, Object> objToMap(Object object) throws IllegalAccessException {

        Class clazz = object.getClass();
        TreeMap<String, Object> treeMap = new TreeMap<>();

        while (null != clazz.getSuperclass()) {
            Field[] declaredFields1 = clazz.getDeclaredFields();

            for (Field field : declaredFields1) {
                String name = field.getName();

                // 获取原来的访问控制权限
                boolean accessFlag = field.isAccessible();
                // 修改访问控制权限
                field.setAccessible(true);
                Object value = field.get(object);
                // 恢复访问控制权限
                field.setAccessible(accessFlag);

                if (null != value && StringUtils.isNotBlank(value.toString())) {
                    //如果是List,将List转换为json字符串
                    if (value instanceof List) {
                        value = JSON.toJSONString(value);
                    }
                    treeMap.put(name, value);
                }
            }

            clazz = clazz.getSuperclass();
        }
        return treeMap;
    }


}
