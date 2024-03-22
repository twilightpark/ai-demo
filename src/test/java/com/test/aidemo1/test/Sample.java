package com.test.aidemo1.test;

import com.baidu.aip.imageclassify.AipImageClassify;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Sample {
    //设置APPID/AK/SK
    public static final String APP_ID = "46441209";
    public static final String API_KEY = "hIRodE9ZrohlBVaPMCnxEWnH";
    public static final String SECRET_KEY = "4QNA9KYR1xDnVIVvKKLFciVqGPj1cite";

    public static void main(String[] args) throws JSONException {
        // 初始化一个AipImageClassify
        AipImageClassify client = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);

        // 调用接口
        String path = "C:\\Users\\11446\\IdeaProjects\\fruit.jpg";
        JSONObject res = client.advancedGeneral(path, new HashMap<String, String>());
        System.out.println(res.toString(2));

    }
}
