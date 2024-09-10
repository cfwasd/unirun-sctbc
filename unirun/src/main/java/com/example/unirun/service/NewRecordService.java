package com.example.unirun.service;

import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import com.example.unirun.dto.NewRecordModel;
import com.example.unirun.pojo.Location;
import com.example.unirun.config.AppConfig;
import com.example.unirun.schoolmap.MapFactory;
import com.example.unirun.utlis.DistanceUtils;
import com.example.unirun.utlis.RandomUtils;
import com.example.unirun.utlis.SignUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * @author wzh
 */
@Service
public class NewRecordService {
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * 跑步总方法入口
     * @param model
     * @return
     * @throws NoSuchAlgorithmException
     * @throws JsonProcessingException
     */
    public String record(NewRecordModel model) throws NoSuchAlgorithmException, JsonProcessingException {
        Double average = this.checkSpeed(model.getRunDistance(), model.getRunTime());
        if (average < 6){
            return "速度过快，请重新输入";
        }else {
            Map map = queryRunStandard(model.getToken(), model.getSchoolId());
            if (map == null){
                return "跑步失败";
            }
            String semesterYear = (String) map.get("semesterYear");
            Map body = runBody(model, semesterYear);
            Map headers = runHeader(model.getToken(), body);
            String result = HttpRequest.post(AppConfig.HOST + "/unirun/save/run/record/new").addHeaders(headers).body(mapper.writeValueAsString(body)).execute().body();
//            System.out.println(result);
            return result;
        }

    }

    public Map runHeader(String token, Map body) throws NoSuchAlgorithmException, JsonProcessingException {
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("token", token);
        headers.put("appKey", AppConfig.APP_KEY);
        headers.put("sign", SignUtil.getSign(null, body));
        headers.put("Content-Type", AppConfig.CONTENT_TYPE);
        headers.put("User-Agent", AppConfig.USER_AGENT);
        return headers;
    }
    public Map runBody(NewRecordModel model,String semesterYear){
        Map<String, Object> data = new LinkedHashMap();
        data.put("againRunStatus", "0");
        data.put("againRunTime", "0");
        data.put("appVersions", AppConfig.APP_VERSION);
        data.put("brand", AppConfig.BRAND);
        data.put("mobileType", AppConfig.MOBILE_TYPE);
        data.put("sysVersions", AppConfig.SYS_VERSION);
        data.put("trackPoints", genTrackPoints(model.getRunDistance(), model.getMapChoice()));
        data.put("distanceTimeStatus", 1);
        data.put("innerSchool", 1);
        data.put("runDistance", model.getRunDistance());
        data.put("runTime", model.getRunTime());
        data.put("userId", model.getUserId());
        data.put("vocalStatus", 1);
        data.put("yearSemester", semesterYear);
        data.put("recordDate", getDate());
        return data;
    }

    public Double checkSpeed(Integer runDistance, Integer runTime){
        return 1.0 * runTime / runDistance * 1000;
    }

    /**
     * 检查登录状态
     * @param token
     * @return
     */
    public boolean checkLoginStatus(String token) {
        try {
            String sign = SignUtil.getSign(null, null);
            LinkedHashMap<String, String> loginHeaders = new LinkedHashMap<>();
            loginHeaders.put("token", token);
            loginHeaders.put("appKey", AppConfig.APP_KEY);
            loginHeaders.put("sign", sign);
            loginHeaders.put("Content-Type", AppConfig.CONTENT_TYPE);
            loginHeaders.put("User-Agent", AppConfig.USER_AGENT);

            String result = HttpRequest.get(AppConfig.HOST + "/auth/query/token").addHeaders(loginHeaders).execute().body();
//        System.out.println("res:"+result);

            Map map = mapper.readValue(result, Map.class);

            if (map.get("code").equals(10000)) {
                return true;
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * 查询标准信息
     * @param token
     * @param schoolId
     * @return
     */

    public Map queryRunStandard(String token, String schoolId){
        if (checkLoginStatus(token)){
            String url = AppConfig.HOST + "/unirun/query/runStandard?schoolId="+schoolId;
            try {
                Map<String, String> query = new LinkedHashMap<>();
                query.put("schoolId", schoolId);
                String sign = SignUtil.getSign(query, null);
                LinkedHashMap<String, String> loginHeaders = new LinkedHashMap<>();
                loginHeaders.put("token", token);
                loginHeaders.put("appKey", AppConfig.APP_KEY);
                loginHeaders.put("sign", sign);
                loginHeaders.put("Content-Type", AppConfig.CONTENT_TYPE);
                loginHeaders.put("User-Agent", AppConfig.USER_AGENT);
                String result = HttpRequest.get(url).addHeaders(loginHeaders).execute().body();
//                System.out.println(result);
                Map map = mapper.readValue(result, Map.class);
                if (map.get("code").equals(10000)) {
                    return (Map) map.get("response");
                }
            }  catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }else {
            return null;
        }

        return null;
    }

    public  String getDate(){
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();

        // 减去30分钟
        LocalDateTime thirtyMinutesAgo = now.minus(30, ChronoUnit.MINUTES);

        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 格式化为字符串
        String formattedDateTime = thirtyMinutesAgo.format(formatter);

        // 输出结果

        return formattedDateTime;
    }

    /**
     * 选择具体地图
     * @param runDistance
     * @param mapChoice
     * @return
     */
    public  String genTrackPoints(Integer runDistance, String mapChoice){
        List<Location> map =  MapFactory.createSchoolMap(mapChoice).getMapData();
        List<Location> locations = new ArrayList<>();
        for (Location d : map) {
            locations.add(new Location(d.getId(), d.getLocation(), d.getEdge()));
        }
        return gen(runDistance, locations);

    }

    /**
     * 路程计算方法
     * @param distance
     * @param locations
     * @return
     */
    private  String gen(Integer distance, List<Location> locations) {
        int currentDistance = 0;
        Location startLocation = locations.get(new Random().nextInt(locations.size()));
        Location currentLocation = startLocation;
        List<String> result = new ArrayList<>();

        long startTime = System.currentTimeMillis() - 30 * 60 * 1000;
        int lastIndex = -1;

        String[] current = currentLocation.getLocation().split(",");
        result.add(current[0] + "-" + current[1] + "-" + startTime + "-" + RandomUtils.randAccuracy());

        while (currentDistance < distance) {
            String[] currentCoords = currentLocation.getLocation().split(",");
            int[] edge = currentLocation.getEdge();

            if (edge.length == 0) {
                System.out.println("edge为空");
                continue;
            }
            int edgeIndex = edge[new Random().nextInt(edge.length)];
            if (edgeIndex == lastIndex) {
                List<Integer> filteredEdges = new ArrayList<>();
                for (int e : edge) {
                    if (e != lastIndex) {
                        filteredEdges.add(e);
                    }
                }
                if (filteredEdges.isEmpty()) {
                    continue;
                }
                edgeIndex = filteredEdges.get(new Random().nextInt(filteredEdges.size()));
            }
            Location nextLocation = locations.get(edgeIndex);

            double[] startData = Arrays.stream(current).mapToDouble(Double::parseDouble).toArray();
            double[] endData = Arrays.stream(nextLocation.getLocation().split(",")).mapToDouble(Double::parseDouble).toArray();

            double goDistance = DistanceUtils.calculateDistance(startData, endData);
            currentDistance += goDistance;

            startTime += (long) (goDistance / RandomUtils.randomInt(1, 5) * 1000);
            String[] end = nextLocation.getLocation().split(",");
            result.add(end[0] + "-" + end[1] + "-" + startTime + "-" + RandomUtils.randAccuracy());

            lastIndex = currentLocation.getId();
            currentLocation = nextLocation;
        }

        startTime += RandomUtils.randomInt(5, 10) * 1000;
        String replacedLocation = currentLocation.getLocation().replace(',', '-');
        result.add(replacedLocation + "-" + startTime + "-" + RandomUtils.randAccuracy());
        try {
            return mapper.writeValueAsString(result);
        } catch (Exception e) {
            return null;
        }
    }

}
