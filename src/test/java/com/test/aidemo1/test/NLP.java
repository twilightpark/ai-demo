package com.test.aidemo1.test;

import com.baidu.aip.nlp.AipNlp;
import com.baidu.aip.nlp.ESimnetType;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class NLP {
    //设置APPID/AK/SK
    public static final String APP_ID = "46455409";
    public static final String API_KEY = "NGBIrGRWNEzGzR1zBCtTsQ1Q";
    public static final String SECRET_KEY = "oAcVtqMKNxNQ8x1ulqWAonp4DDcxqc6M";

    public static void main(String[] args) throws JSONException {
        // 初始化一个AipNlp
        AipNlp client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);


        // 调用接口
        /**
         * 1.词法分析
         * 词法分析接口向用户提供分词、词性标注、专名识别三大功能；能够识别出文本串中的基本词汇（分词），
         * 对这些词汇进行重组、标注组合后词汇的词性，并进一步识别出命名实体
         * */
        System.out.println("------------词法分析------------");
        String text1 = "百度是一家高科技公司";
        JSONObject res1 = client.lexer(text1, null);
        System.out.println(res1.toString(2));

        /**
         * 2、依存句法分析
         * 依存句法分析接口可自动分析文本中的依存句法结构信息，利用句子中词与词之间的依存关系来表示词语的句法结构信息
         * （如“主谓”、“动宾”、“定中”等结构关系），并用树状结构来表示整句的结构（如“主谓宾”、“定状补”等
         * **/
        System.out.println("------------依存句法分析------------");
        // 传入可选参数调用接口
        String text2 = "今天天气怎么样";
        HashMap<String, Object> options2 = new HashMap<String, Object>();
        options2.put("mode", 1);

        // 依存句法分析
        JSONObject res2 = client.depParser(text2, options2);
        System.out.println(res2.toString(2));

        /**
         * 3、词向量表示
         * 词向量表示接口提供中文词向量的查询功能。
         * **/
        System.out.println("------------词向量表示------------");

        String word3 = "张飞";

        // 传入可选参数调用接口
        HashMap<String, Object> options3 = new HashMap<String, Object>();

        // 词向量表示
        JSONObject res3 = client.wordEmbedding(word3, options3);
        System.out.println(res3.toString(2));

        /**
         * 4、DNN语言模型
         * 中文DNN语言模型接口用于输出切词结果并给出每个词在句子中的概率值,
         * 判断一句话是否符合语言表达习惯。
         * **/
        System.out.println("------------DNN语言模型------------");

        String text4 = "床前明月光";

        // 传入可选参数调用接口
        HashMap<String, Object> options4 = new HashMap<String, Object>();

        // DNN语言模型
        JSONObject res4 = client.dnnlmCn(text4, options4);
        System.out.println(res4.toString(2));

        /**
         * 5、词义相似度
         * 输入两个词，得到两个词的相似度结果。
         * **/
        System.out.println("------------词义相似度------------");

        String word1 = "北京";
        String word2 = "上海";

        // 传入可选参数调用接口
        HashMap<String, Object> options5 = new HashMap<String, Object>();


        // 词义相似度
        JSONObject res5 = client.wordSimEmbedding(word1, word2, options5);
        System.out.println(res5.toString(2));
        /**
         * 6、短文本相似度
         * 短文本相似度接口用来判断两个文本的相似度得分。
         * **/
        System.out.println("------------短文本相似度------------");

        String text6_1 = "浙富股份";
        String text6_2 = "万事通自考网";

        // 传入可选参数调用接口
        HashMap<String, Object> options6 = new HashMap<String, Object>();
        options6.put("model", "CNN");

        // 短文本相似度
        JSONObject res6 = client.simnet(text6_1, text6_2, options6);
        System.out.println(res6.toString(2));

        /**
         * 7、评论观点抽取
         * 评论观点抽取接口用来提取一条评论句子的关注点和评论观点，
         * 并输出评论观点标签及评论观点极性。
         * **/
        System.out.println("------------评论观点抽取------------");

        String text7 = "三星电脑电池不给力";
        HashMap<String, Object> options7 = new HashMap<String, Object>();

        JSONObject response7 = client.commentTag(text7, ESimnetType.LIFE, options7);
        System.out.println(response7.toString());

        // 获取美食评论情感属性
        response7 = client.commentTag("这家餐馆味道不错", ESimnetType.FOOD, options7);
        System.out.println(response7.toString());

        // 获取酒店评论情感属性
        response7 = client.commentTag("喜来登酒店不错", ESimnetType.HOTEL, options7);
        System.out.println(response7.toString());

        /**
         * 8、情感倾向分析
         * 对包含主观观点信息的文本进行情感极性类别（积极、消极、中性）的判断，
         * 并给出相应的置信度。
         * **/
        System.out.println("------------情感倾向分析------------");

        String text8 = "苹果是一家伟大的公司";

        // 传入可选参数调用接口
        HashMap<String, Object> options8 = new HashMap<String, Object>();

        // 情感倾向分析
        JSONObject res8 = client.sentimentClassify(text8, options8);
        System.out.println(res8.toString(2));

        /**
         * 9、文章标签
         * 文章标签服务能够针对网络各类媒体文章进行快速的内容理解，
         * 根据输入含有标题的文章，输出多个内容标签以及对应的置信度，
         * 用于个性化推荐、相似文章聚合、文本内容分析等场景。
         * **/
        System.out.println("------------文章标签------------");

        String title = "iphone手机出现“白苹果”原因及解决办法，用苹果手机的可以看下";
        String content = "如果下面的方法还是没有解决你的问题建议来我们门店看下成都市锦江区红星路三段99号银石广场24层01室。";

        // 传入可选参数调用接口
        HashMap<String, Object> options9 = new HashMap<String, Object>();

        // 文章标签
        JSONObject res9 = client.keyword(title, content, options9);
        System.out.println(res9.toString(2));

        /**
         * 10、文章分类
         * 对文章按照内容类型进行自动分类，首批支持娱乐、体育、科技等26个主流内容类型，
         * 为文章聚类、文本内容分析等应用提供基础技术支持
         * **/
        System.out.println("------------文章分类------------");
        String title10 = "欧洲冠军杯足球赛";
        String content10 = "欧洲冠军联赛是欧洲足球协会联盟主办的年度足球比赛，代表欧洲俱乐部足球最高荣誉和水平，被认为是全世界最高素质、最具影响力以及最高水平的俱乐部赛事，亦是世界上奖金最高的足球赛事和体育赛事之一。";

        // 传入可选参数调用接口
        HashMap<String, Object> options10 = new HashMap<String, Object>();

        // 文章分类
        JSONObject res10 = client.topic(title10, content10, options10);
        System.out.println(res10.toString(2));

        /**
         *11、文本纠错
         * 识别输入文本中有错误的片段，提示错误并给出正确的文本结果。
         * 支持短文本、长文本、语音等内容的错误识别，纠错是搜索引擎、语音识别、内容审查等功能更好运行的基础模块之一。
         * **/
        System.out.println("------------文本纠错------------");
        String text11 = "百度是一家人工只能公司";

        // 传入可选参数调用接口
        HashMap<String, Object> options11 = new HashMap<String, Object>();

        // 文本纠错
        JSONObject res11 = client.ecnet(text11, options11);
        System.out.println(res11.toString(2));


    }
}
