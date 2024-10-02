package com.sparta.schedule_management.util;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Timestamp;


public class Util {
    public static String encryptPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static boolean checkPassword(String plainPassword, String encryptedPassword) {
        return BCrypt.checkpw(plainPassword, encryptedPassword);
    }

    public static int convertTimestampIntoInt(Timestamp timestamp) {
        // timestamp 초 단위로 변경
        return (int) (timestamp.getTime() / 1000);
    }
}
