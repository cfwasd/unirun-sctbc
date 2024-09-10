package com.example.unirun.schoolmap;

import com.example.unirun.pojo.Location;

import java.util.List;

/**
 * @author wzh
 * 地图抽象类
 */
public interface SchoolMap {
    List<Location> getMapData();
}
