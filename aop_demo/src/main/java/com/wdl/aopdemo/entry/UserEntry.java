package com.wdl.aopdemo.entry;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserEntry implements Serializable {
    private String userId;
    private String passWord;
}
