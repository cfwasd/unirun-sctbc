package com.example.unirun.pojo;

import java.util.Arrays;

/**
 * @author wzh
 * 地图经纬度容器
 */
public class Location {
    private int id;
    private String location;
    private int[] edge;

    public Location(int id, String location, int[] edge) {
        this.id = id;
        this.location = location;
        this.edge = edge;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int[] getEdge() {
        return edge;
    }

    public void setEdge(int[] edge) {
        this.edge = edge;
    }

    @Override
    public String toString() {
        return "MapData{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", edge=" + Arrays.toString(edge) +
                '}';
    }
}
