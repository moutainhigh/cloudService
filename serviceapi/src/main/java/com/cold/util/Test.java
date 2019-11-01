package com.cold.util;

import com.cold.pojo.PatentVo;
import com.cold.pojo.SearchRequest;

import java.util.List;

/**
 * @Auther: ohj
 * @Date: 2019/10/23 08:28
 * @Description:
 */
public class Test {
    public static void main(String[] args) {
        String source = "在 从 购买 的 硝酸铋制 备氧化铋 的 情况 下 , 硝酸铋 价格 高 且 硝酸铋 不 是 容易 获得 的 .";
        String trans = "In the case of preparing bismuth oxide from purchased bismuth nitrate , the price of bismuth nitrate is high and bismuth nitrate is not easily available .";
        String aligned = "0-0 1-7 2-8 3-14 4-4 4-5 4-9 4-15 5-6 5-10 5-16 6-1 6-3 7-2 8-2 8-12 9-11 10-15 11-13 12-18 13-19 14-20 14-21 15-22 15-23 16-17 16-22 17-24 18-25 19-14 20-26";
        String kw = "硝酸铋";
        source = "for users with small hands or with disabled hands ,";
        trans = "对于 手 小 或 手 有 残疾 的 用户 而言 ，";
        aligned = "0-0 4-1 3-2 5-3 8-4 6-5 7-6 2-7 1-8 1-9 9-10";
        kw = "hands or with";
        source = "R_S is the challenge be in one ' s hands and be sent to authentication token in means ( step 1604 , Figure 16 ) by token voucher server Value.";
        trans = "R_S 是 在握 手段 ( 步骤 1604 , 图 16 ) 中 由 令牌 凭证 服务器 发送到 认证 令牌 的 挑战 值 。";
        aligned = "0-0 1-1 0-2 16-2 17-3 18-4 19-5 20-6 21-7 22-8 23-9 24-10 25-12 26-13 27-14 28-15 12-16 13-16 14-17 15-18 2-19 3-20 29-21 29-22";

        source = "\" solidification \" and similar terms mean that polymer is subjected to or is exposed to before or after it is configured to product induction and hands over The processing of connection.";
        trans = "\" 固化 \" 和 类似 术语 意指 聚合物 在其 成形 为 制品 之前 或 之后 经受 或 暴露 于 诱导 交联 的 处理 。";
        aligned = "0-0 1-1 2-2 3-3 4-4 5-5 6-6 7-6 8-7 19-8 21-9 21-10 22-10 23-11 16-12 17-13 18-14 10-15 11-15 12-16 14-17 14-18 15-18 24-19 26-20 27-20 28-21 29-22 30-23 31-23";
        kw = "similar terms mean that polymer";
        PatentVo patentVo = new PatentVo();
        patentVo.setSource(source);
        patentVo.setTrans(trans);
        patentVo.setAligned(aligned);
        List<String> transList = AlignUtil.getAlignTrans(kw,patentVo,true);
        System.out.println(patentVo.getTrans());
//        SearchRequest searchRequest = new SearchRequest();
//        searchRequest.setSrcLan("en");
//        searchRequest.setTgtLan("zh");
//        searchRequest.setKw(kw);
//        searchRequest.setCount(100);
        //JSONObject.toJSONString(object);
        System.out.println(transList);
    }
}