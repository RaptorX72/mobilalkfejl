package com.example.questionare;
import java.util.UUID;
public class Common {
    public static String GenerateUIID() {
        return UUID.randomUUID().toString();
    }
}
