package com.example.unirun.schoolmap;

import com.example.unirun.pojo.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wzh
 * 地图实现类（四川工商职业技术学院）
 */
public class Sctbc implements SchoolMap{
    @Override
    public List<Location> getMapData() {
        List<Location> mapData = new ArrayList<>();
        mapData.add(new Location(1, "103.69514666497707,30.914403235156513", new int[]{1}));
        mapData.add(new Location(2, "103.69557581841946,30.914062662412995", new int[]{2}));
        mapData.add(new Location(3, "103.69617663323879,30.91386936383013", new int[]{3}));
        mapData.add(new Location(4, "103.69670234620571,30.91366686013397", new int[]{4}));
        mapData.add(new Location(5, "103.69727097451687,30.913086960814983", new int[]{5}));
        mapData.add(new Location(6, "103.69750700891018,30.912212502815343", new int[]{6}));
        mapData.add(new Location(7, "103.69665943086147,30.912626720758688", new int[]{7}));
        mapData.add(new Location(8, "103.69560800492764,30.913123779923833", new int[]{8}));
        mapData.add(new Location(9, "103.69507156312466,30.913409127537193", new int[]{9}));
        mapData.add(new Location(10, "103.6950608342886,30.913998229595432", new int[]{0}));

        return mapData;
    }
}
