package com.test;

import org.apache.shiro.crypto.hash.SimpleHash;

public class Test {
    public static void main(String[] args) {
        String str = "md5";
        String paw = "小兰123";
        SimpleHash simpleHash = new SimpleHash(str, paw, null, 2);
        System.out.println(simpleHash);
    }
}
