package com.example.unirun.utlis;


import net.sf.geographiclib.Geodesic;

/**
 * @author wzh
 * 经纬度转换成距离
 */
public class DistanceUtils {
    public static double calculateDistance(double[] start, double[] end) {


        return Geodesic.WGS84.Direct(start[0], start[1], end[0], end[1]).s12;
    }
}
