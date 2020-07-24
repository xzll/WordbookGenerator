package com.yj.wg;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.io.*;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Generator {
    public static void main(String[] args) throws UnsupportedEncodingException {
//        generator("","","");
    }

    /**
     * 简单生成有道生词本
     * @param pathFrom 文本路径，文本是"单词或词组 解释"的格式
     * @param pathTo 生成的文本路径
     * @param group 有道中的分组
     * @throws UnsupportedEncodingException
     */
    public static void generator(String pathFrom,String pathTo,String group) throws UnsupportedEncodingException {
        pathFrom= URLDecoder.decode(pathFrom,"utf-8");
        pathTo= URLDecoder.decode(pathTo,"utf-8");
        StringBuilder sb = new StringBuilder();
        sb.append("<wordbook>");
        try(BufferedReader br = new BufferedReader(new FileReader(pathFrom));
        BufferedWriter bw = new BufferedWriter(new FileWriter(pathTo))) {//TODO 还是要小心文件覆盖，之后再改了
            String line;
            while ((line = br.readLine())!=null ){
//                String[] split = line.split("([a-z]+\\s)+");
//                Matcher m = Pattern.compile("(([a-z]+\\s)+)").matcher(line);
//                String word = m.group(0);
                sb.append("<item><word>");
                String[] split = line.split("\\s");
                int i = 0;
                while(i<split.length&&isLetter(split[i].charAt(0))){
                    sb.append(split[i]);
                    sb.append(" ");
                    i++;
                }
                sb.append("</word>\n" +
                        "<trans><![CDATA[");
                while (i<split.length){
                    sb.append(split[i]);
                    sb.append(" ");
                    i++;
                }
                sb.append("]]></trans><phonetic><![CDATA[[]]]></phonetic><tags>");
                sb.append(group);
                sb.append("</tags>\n" +
                        "<progress>1</progress>\n" +
                        "</item>");
            }
            sb.append("</wordbook>");
            bw.write(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static boolean isLetter(char c){
        return (c>='a'&& c<='z') || (c>='A'&& c<='Z');
    }
}
