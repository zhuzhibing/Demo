package net.luculent.automatioin.laks.services.article;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

/**
 * @Author: zhuzb
 * @Description:
 * @Date Create In 17:33 2018/4/23
 * @Modified By:
 */

@RestController
@RequestMapping(value = "/services")
public class MapDataController {


    @GetMapping(value = "/public/mapData/get")
    public String getMapData(){

        String fileName = "E:/mapdata/molinpoint.json";
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }


    }
}
