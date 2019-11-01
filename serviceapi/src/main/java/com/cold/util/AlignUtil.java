package com.cold.util;

import com.cold.pojo.PatentVo;
import com.cold.serviceapi.domain.TermVo;
import com.cold.utils.StringUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: ohj
 * @Date: 2019/10/23 8:11
 * @Description:
 */
public class AlignUtil {
    /**
     * 匹配的术语
     * @param source
     * @param kw
     * @return
     */
    public static List<TermVo> getMatches(String source,String kw){
        Pattern pattern = Pattern.compile(kw);
        Matcher matcher = pattern.matcher(source);
        List<TermVo> termVoList = new ArrayList<>();
        while (matcher.find()){
            String s =matcher.group();
            int start = matcher.start();
            int end = matcher.end();
            if(end+1<source.length()){
                String next = source.substring(end,end+1);
                if(!next.equals(" ")){
                    continue;
                }
            }
            int segIndex = StringUtil.getSegIndex(source,start);//分词位置
            TermVo termVo = new TermVo(s,start,matcher.end(),segIndex);
            termVoList.add(termVo);
        }
        return termVoList;
    }

    /**
     * 根据对齐关系获取翻译
     * @param kw
     * @param patentVo
     * @return
     */
    public static List<String> getAlignTrans(String kw, PatentVo patentVo,boolean removeTransBlank){
        String source = patentVo.getSource();
        String trans = patentVo.getTrans();
        String aligned = patentVo.getAligned();//对齐关系
        //Set<Integer> transSet = new TreeSet<Integer>((o1, o2) -> o2.compareTo(o1));
        List<String> transList = Lists.newArrayList();
        String[] transSegList = StringUtils.split(trans," ");
        String[] highlightTransList = transSegList.clone();
        Map<Integer, Collection<Integer>> map = CorrespondUtil.PretreatmentCorrespond(aligned);
        List<TermVo> termVoList = getMatches(source,kw);
        termVoList.forEach(termVo -> {
            String segTrans = "";
            int segCount = StringUtils.countMatches(kw," ");//分词个数
            int segIndex = termVo.getSegIndex();
            List<Integer> matchedIndex = Lists.newArrayList();
            for(int i = 0;i<=segCount;i++){
                if(!map.containsKey(segIndex+i)){
                    continue;
                }
                Collection<Integer> transIndexList = map.get(segIndex+i);
                int j = 0;
                for (Integer index : transIndexList){
                    if(matchedIndex.contains(index)){
                        continue;
                    }
                    if(j>0&&index -j >2){
                        if(StringUtils.isNotBlank(segTrans))segTrans+="...";
                    }
                    segTrans+=transSegList[index]+" ";
                    highlightTransList[index] = "<em>"+transSegList[index]+"</em>";
                    j = index;
                }
                matchedIndex.addAll(transIndexList);
                //transIndexList.sort((a, b) -> a - b);

            }
            if(StringUtils.isNotBlank(segTrans))transList.add(segTrans);
        });
        String highlightTrans = StringUtils.join(highlightTransList," ");
        if(removeTransBlank){
            highlightTrans = StringUtil.removeBlank(highlightTrans);
        }
        patentVo.setTrans(highlightTrans);
        return transList;
    }
}