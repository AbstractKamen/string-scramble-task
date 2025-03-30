package com.abstractkamen.tasks.impl;

public class Formats {
    private Formats(){}
    public static String getFormattedMillisTime(long beginTime, long endTime) {
        final String readableFormat = "%02d:%02d:%02d.%03d";
        long millis = endTime - beginTime;
        int sec = (int) (millis / 1000) % 60;
        int min = (int) ((millis / (1000 * 60)) % 60);
        int hours = (int) ((millis / (1000 * 60 * 60)) % 24);
        return String.format(readableFormat, hours, min, sec, millis % 1000);
    }
}
