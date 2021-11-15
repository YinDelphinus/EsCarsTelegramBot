package com.escars.model;

import lombok.Data;

import java.util.List;

@Data
public class SendMessageRequest {
    private String htmlText;
    private List<String> chatIds;
}
