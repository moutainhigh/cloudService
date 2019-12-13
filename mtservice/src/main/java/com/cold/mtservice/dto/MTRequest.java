package com.cold.mtservice.dto;


import lombok.Data;
/**
 * @Auther: ohj
 * @Date: 2019/5/5 09:07
 * @Description:
 */
@Data
public class MTRequest {
    private Long userId;
    private String from;
    private String to;
    private String originalText;
    private String token;
    private Long timestamp;
}