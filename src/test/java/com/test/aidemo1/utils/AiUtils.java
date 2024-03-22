package com.test.aidemo1.utils;



import java.io.IOException;
import java.util.HashMap;

import com.baidu.aip.imageclassify.AipImageClassify;
import com.baidu.aip.nlp.AipNlp;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.baidu.aip.ocr.AipOcr;
import org.springframework.web.multipart.MultipartFile;

public class AiUtils  {
    //设置APPID/AK/SK
    public static final  String APP_ID_PIC= "46178170";
    public static final  String API_KEY_PIC = "gmSh8Z1gcMdLOhNmfDKdlGz1";
    public static final  String SECRET_KEY_PIC= "Yh5zQGSRNh8OECs6qmMS0iYMtTen1NZi";

    public static final String APP_ID_IMG = "46441209";
    public static final String API_KEY_IMG = "hIRodE9ZrohlBVaPMCnxEWnH";
    public static final String SECRET_KEY_IMG = "4QNA9KYR1xDnVIVvKKLFciVqGPj1cite";

    public static final String APP_ID_NLPCORRECTION = "46455409";
    public static final String API_KEY_NLPCORRECTION = "NGBIrGRWNEzGzR1zBCtTsQ1Q";
    public static final String SECRET_KEY_NLPCORRECTION= "oAcVtqMKNxNQ8x1ulqWAonp4DDcxqc6M";

    public static String picToWords(MultipartFile file) throws IOException, JSONException {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID_PIC, API_KEY_PIC, SECRET_KEY_PIC);
        // 调用接口
        JSONObject res = client.basicGeneral(file.getBytes(), new HashMap<String, String>());
        JSONArray words_result = res.getJSONArray("words_result");
        String result="";
        for(int i=0;i<words_result.length();i++)
        {
//            System.out.println(words_result.getJSONObject(i));
            JSONObject jsonObject = words_result.getJSONObject(i);
            Object words = jsonObject.get("words");
            String s=(String)words;
            result+=s+" ";

        }

        return result;
    }

    public static String imgRecognition(MultipartFile file) throws IOException, JSONException{
// 初始化一个AipImageClassify
        AipImageClassify client = new AipImageClassify(APP_ID_IMG, API_KEY_IMG, SECRET_KEY_IMG);

        // 调用接口
        JSONObject res = client.advancedGeneral(file.getBytes(), new HashMap<String, String>());
        return res.toString(2);
        /**
         * 在后端取出并处理数据的代码如下
         * 项目本身用前端代码处理
         * **/
       // System.out.println(res.toString(2));
//        JSONArray words_result = res.getJSONArray("result");
//        String result="";
//        for(int i=0;i<words_result.length();i++)
//        {
////            System.out.println(words_result.getJSONObject(i));
//            JSONObject jsonObject = words_result.getJSONObject(i);
//            Object words = jsonObject.get("keyword");
//            String s=(String)words;
//            result +=s+" ";
//
//        }
//        return result;
    }
    public static String NLPCorrection(String text) throws  JSONException{
        AipNlp client = new AipNlp(APP_ID_NLPCORRECTION, API_KEY_NLPCORRECTION, SECRET_KEY_NLPCORRECTION);

        System.out.println("------------文本纠错------------");
        //String text11 = "百度是一家人工只能公司";

        // 传入可选参数调用接口
        HashMap<String, Object> options11 = new HashMap<String, Object>();

        // 文本纠错
        JSONObject res = client.ecnet(text, options11);
        System.out.println(res.getJSONObject("item").get("correct_query"));

        //System.out.println(res.toString(2));
        return res.getJSONObject("item").get("correct_query").toString();


    }




}
