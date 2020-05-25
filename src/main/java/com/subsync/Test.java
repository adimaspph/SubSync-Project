package com.subsync;

import com.subsync.DataBase.InvalidTimeException;
import com.subsync.DataBase.SRTFile;
import com.subsync.DataBase.SubSync;
import com.subsync.DataBase.Time;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) {

        try {
            FileReader sub = new FileReader("C:\\Users\\Engineer\\Documents\\Java Adimas\\Springboot Project\\src\\main\\java\\com\\subsync\\Burning (2018).srt");
            SubSync test = new SRTFile(new BufferedReader(sub));
//            test.sync("+", 1,1,50,900);
//            test.removeTag();

            File myObj = test.download();
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

//    public static void sync(String indicator, int hour, int minute, int second, int miliSecond) throws InvalidTimeException {
//        String line = "00:01:17,290 --> 00:01:19,029";
//        System.out.println(line);
//        if (line.contains("-->")) {
//            String[] sliced = line.split(" ");
//            ArrayList<String> slicedList = new ArrayList<>(Arrays.asList(sliced));
//
//            String[] start = slicedList.get(0).split(":|\\,");
//            String[] stop = slicedList.get(2).split(":|\\,");
//
//            Time startTime = new Time(start[0], start[1], start[2], start[3]);
//            Time stopTime = new Time(stop[0], stop[1], stop[2], stop[3]);
//
//            if (indicator.equals("+")) {
//                startTime.increaseTime(hour, minute, second, miliSecond);
//                stopTime.increaseTime(hour, minute, second, miliSecond);
//            } else if (indicator.equals("-")){
//                startTime.decreaseTime(hour, minute, second, miliSecond);
//                stopTime.decreaseTime(hour, minute, second, miliSecond);
//            }
//
//            line = String.format("%s --> %s", startTime.toStringWithComma(), stopTime.toStringWithComma());
//
//            System.out.println(line);
//        }
//
//    }
}
