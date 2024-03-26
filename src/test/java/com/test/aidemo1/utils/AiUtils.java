package com.test.aidemo1.utils;



import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

import com.baidu.aip.imageclassify.AipImageClassify;
import com.baidu.aip.nlp.AipNlp;

import com.baidu.aip.nlp.ESimnetType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.baidu.aip.ocr.AipOcr;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;

import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import java.io.ByteArrayOutputStream;



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

    public static final String APP_ID_NLPVIEWS= "46455409";
    public static final String API_KEY_NLPVIEWS = "NGBIrGRWNEzGzR1zBCtTsQ1Q";
    public static final String SECRET_KEY_NLPVIEWS= "oAcVtqMKNxNQ8x1ulqWAonp4DDcxqc6M";

    public static final  String API_KEY_VOCAL = "0u1n586osQVaHsdbH9MXbWN6";
    public static final  String SECRET_KEY_VOCAL= "EsAdq5wesjbnWkaVWB3h5uMDubb9QwqG";
    private final boolean METHOD_RAW = false; // 默认以json方式上传音频文件
    // 需要识别的文件
    private static final String FILENAME = "16k.pcm";
    // 文件格式, 支持pcm/wav/amr 格式，极速版额外支持m4a 格式
    private static final String FORMAT = FILENAME.substring(FILENAME.length() - 3);
    private static String CUID = "AE-B6-D0-BB-8D-8B";
    // 采样率固定值
    private static final int RATE = 16000;
    private static String URL;
    private static int DEV_PID;
    private static String SCOPE;
    private static String token;
    /**
     * 当前的时间戳，毫秒
     */
    private static long expiresAt;

    {
        URL = "https://vop.baidu.com/server_api"; // 可以改为https
        //  1537 表示识别普通话，使用输入法模型。 其它语种参见文档
        DEV_PID = 1537;
        SCOPE = "audio_voice_assistant_get";
    }


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

    /**语音识别相关代码*/
    public static String vocalRecognition(MultipartFile file) throws IOException, JSONException,DemoException{
        byte[] bytes = file.getBytes();
        String base64File= Base64.getEncoder().encodeToString(bytes);
        String originalFilename = file.getOriginalFilename();
        String format="";
        if (originalFilename != null) {
            int lastDotIndex = originalFilename.lastIndexOf('.');
            if (lastDotIndex != -1) {
                format = originalFilename.substring(lastDotIndex + 1);
            }
        }
//        System.out.println("format=" + format);

        String result = null;
        String url_token = "http://aip.baidubce.com/oauth/2.0/token";
        String getTokenURL = url_token + "?grant_type=client_credentials"
                + "&client_id=" + urlEncode(API_KEY_VOCAL) + "&client_secret=" + urlEncode(SECRET_KEY_VOCAL);

        // 打印的url出来放到浏览器内可以复现
//        System.out.println("token url:" + getTokenURL);

        URL url = new URL(getTokenURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        result =  new String(getResponseBytes(conn));
//        System.out.println("Token result json:" + result);
        parseJson(result);




        result = runJsonPostMethod(token,bytes,base64File,format);

        return result;
    }
    public static String runJsonPostMethod(String token, byte[] bytes, String base64File,String format) throws DemoException, IOException,JSONException {
        JSONObject params = new JSONObject();
        params.put("dev_pid", 1537);
        //params.put("lm_id",LM_ID);//测试自训练平台需要打开注释
        params.put("format", format);
        params.put("rate", 16000);
        params.put("token", token);
        params.put("cuid", "AE-B6-D0-BB-8D-8B");
        params.put("channel", "1");
        params.put("len", bytes.length);
        params.put("speech", base64File);

//        System.out.println(params.toString());
        HttpURLConnection conn = (HttpURLConnection) new URL("https://vop.baidu.com/server_api").openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setDoOutput(true);
        conn.getOutputStream().write(params.toString().getBytes());
        conn.getOutputStream().close();
        String result =new String(getResponseBytes(conn));


        params.put("speech", "base64File");
//        System.out.println("url is : " + "https://vop.baidu.com/server_api");
//        System.out.println("params is :" + params.toString());



        return result;
    }
    private static void parseJson(String result) throws DemoException, JSONException {
        JSONObject json = new JSONObject(result);
        if (!json.has("access_token")) {
            // 返回没有access_token字段
            throw new DemoException("access_token not obtained, " + result);
        }
        if (!json.has("scope")) {
            // 返回没有scope字段
            throw new DemoException("scopenot obtained, " + result);
        }
        // scope = null, 忽略scope检查

        if (SCOPE != null && !json.getString("scope").contains(SCOPE)) {
            throw new DemoException("scope not exist, " + SCOPE + "," + result);
        }
        token = json.getString("access_token");
        expiresAt = System.currentTimeMillis() + json.getLong("expires_in") * 1000;
    }
    public static byte[] getInputStreamContent(InputStream is) throws IOException {
        byte[] b = new byte[1024];
        // 定义一个输出流存储接收到的数据
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 开始接收数据
        int len = 0;
        while (true) {
            len = is.read(b);
            if (len == -1) {
                // 数据读完
                break;
            }
            byteArrayOutputStream.write(b, 0, len);
        }
        return byteArrayOutputStream.toByteArray();
    }
    public static byte[] getResponseBytes(HttpURLConnection conn) throws IOException, DemoException {
        int responseCode = conn.getResponseCode();
        InputStream inputStream = conn.getInputStream();
        if (responseCode != 200) {
            System.err.println("http 请求返回的状态码错误，期望200， 当前是 " + responseCode);
            if (responseCode == 401) {
                System.err.println("可能是appkey appSecret 填错");
            }
            System.err.println("response headers" + conn.getHeaderFields());
            if (inputStream == null) {
                inputStream = conn.getErrorStream();
            }
            byte[] result = getInputStreamContent(inputStream);
            System.err.println(new String(result));

            throw new DemoException("http response code is" + responseCode);
        }

        byte[] result = getInputStreamContent(inputStream);
        return result;
    }
    public static String urlEncode(String str) {
        String result = null;
        try {
            result = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**语音识别相关代码结束**/

    public static String NLPViews(String text) throws  JSONException{
        AipNlp client = new AipNlp(APP_ID_NLPCORRECTION, API_KEY_NLPCORRECTION, SECRET_KEY_NLPCORRECTION);

        System.out.println("------------观点提取------------");
        HashMap<String, Object> options7 = new HashMap<String, Object>();

        JSONObject res = client.commentTag(text, ESimnetType.SHOPPING, options7);

        System.out.println(res.toString());
        JSONArray items=res.getJSONArray("items");
        String result="";
        for (int i = 0; i < items.length(); i++)
        {
            try {
                // 获取当前索引处的 JSONObject
                JSONObject item = items.getJSONObject(i);

                // 获取JSONObject中的元素
                int sentiment = item.getInt("sentiment");
                if(sentiment==2)
                {
                    result+="积极的评价：";
                }else if(sentiment==1)
                {
                    result+="中性的评价：";
                }else
                {
                    result+="消极的评价：";
                }
                result+=item.getString("prop");
                result+=item.getString("adj");
                result+="\n";

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        System.out.println("result="+result);
            return result;


    }




}
