package huan.yan.myproject;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Picther {
    public static void main(String[] args) throws Exception {

        File file = new File("C:\\Users\\huan.yan.XOR\\Desktop/1558942077.png");


        long size = file.length();
        double scale = 1.0d;
        if (size >= 200 * 1024) {
            if (size > 0) {
                scale = (200 * 1024f) / size;
            }
        }


        //拼接文件路劲
        String thumbnailFilePathName = "C:\\Users\\huan.yan.XOR\\Desktop" + "aabb.png";
        File file1 = new File(thumbnailFilePathName);
        if(!file1.exists()) file1.mkdir();

        try {
            //added by chenshun 2016-3-22 注释掉之前长宽的方式，改用大小
//            Thumbnails.of(filePathName).size(width, height).toFile(thumbnailFilePathName);
            if (size < 200 * 1024) {
                Thumbnails.of(file).scale(0.2f).outputFormat("jpg").toFile(file1);
            } else {
                Thumbnails.of(file).scale(1f).outputQuality(scale).outputFormat("jpg").toFile(thumbnailFilePathName);
            }

        } catch (Exception e1) {
//            return new BaseResult(false, "操作失败", e1.getMessage());
        }
        /**
         * 缩略图end
         */


    }
}
