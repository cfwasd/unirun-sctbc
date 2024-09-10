package com.example.unirun.schoolmap;

/**
 * @author wzh
 * @date 2023/10/19
 * 地图工厂类
 */
public class MapFactory {
    public static SchoolMap createSchoolMap(String schoolName){
        if ("sctbc".equals(schoolName)){
            return new Sctbc();
        }
        //TODO:其他学校地图
        else{
            throw new IllegalArgumentException("暂无该学校地图： " + schoolName);
        }
    }
}
