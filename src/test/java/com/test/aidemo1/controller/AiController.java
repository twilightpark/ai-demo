package com.test.aidemo1.controller;

import com.test.aidemo1.utils.AiUtils;
import com.test.aidemo1.utils.DemoException;
import org.json.JSONException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin
public class AiController {
    @RequestMapping(value = "/pic",method = RequestMethod.POST)
    public  String PicToWord(@RequestParam("file") MultipartFile file) throws IOException, JSONException {
        String res = AiUtils.picToWords(file);
        System.out.println(res);
        return res;

    }

    @RequestMapping(value = "/img",method = RequestMethod.POST)
    public  String ImgRecognition(@RequestParam("file") MultipartFile file) throws IOException, JSONException {
        String res = AiUtils.imgRecognition(file);
        return res;

    }

    @RequestMapping(value = "/correction",method = RequestMethod.GET)
        public  String NLPCorrect(@RequestParam("text") String text) throws JSONException {
        String res = AiUtils.NLPCorrection(text);
        return res;

    }
    @RequestMapping(value = "/views",method = RequestMethod.GET)
    public  String NLPViews(@RequestParam("text") String text) throws JSONException {
        String res = AiUtils.NLPViews(text);
        return res;
    }
    @RequestMapping(value = "/vocal",method = RequestMethod.POST)
    public  String VocalRecognition(@RequestParam("file") MultipartFile file) throws IOException, JSONException, DemoException {
        String res = AiUtils.vocalRecognition(file);
//        System.out.println(res);
        return res;

    }
}
