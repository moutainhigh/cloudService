package com.cold.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @Auther: ohj
 * @Date: 2019/10/12 10:22
 * @Description:
 */
public class StringUtil {
    /**
     * 去掉中文中的空格
     * @param source
     * @return
     */
    public static String removeBlank(String source){
        String[] splits =  StringUtils.split(source);
        int i=0;
        while(i<splits.length){
            String s = splits[i];
            if(i+1<splits.length){
                String next = splits[i+1];
                boolean currentType = s.matches("[a-zA-Z]+");
                boolean nextType = next.matches("[a-zA-Z]+");
                boolean nextNum = next.matches("\\d+");
                if((currentType&&nextType)||(currentType&&nextNum)){
                    splits[i] =  s+" ";
                }
            }
            i++;
        }
        return StringUtils.join(splits);
    }

    /**
     * 获取分词位置
     * @param source
     * @param indexOfSource
     * @return
     */
    public static int getSegIndex(String source,int indexOfSource){
        int segIndex = 0;
        if(indexOfSource>0){
            String prefixStr = source.substring(0,indexOfSource);
            segIndex = StringUtils.countMatches(prefixStr," ");
        }
        return segIndex;
    }


}