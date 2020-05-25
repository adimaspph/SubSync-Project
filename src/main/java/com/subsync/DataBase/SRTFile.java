package com.subsync.DataBase;


import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;


public class SRTFile extends SubSync{


    public SRTFile(MultipartFile file) {
        super(file);
    }

    public SRTFile(BufferedReader buff) {
        super(buff);
    }

    @Override
    public void removeTag() {
        try{
            String result = "";
            String line = "";
            while ((line = file.readLine()) != null){
                if (line.contains("</")) {
                    String[] sliced = line.split("");
                    ArrayList<String> slicedList = new ArrayList<>(Arrays.asList(sliced));

                    line = "";
                    boolean tag = false;
                    for (String character : slicedList ) {
                        if (character.equals("<")) {
                            tag = true;
                        }

                        if (!tag) {
                            line += character;
                        }

                        if (character.equals(">")) {
                            tag = false;
                        }
                    }
                }
                result += (line + "\r\n");

            }
            output = result;
            reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sync(String indicator, int hour, int minute, int second, int miliSecond) throws InvalidTimeException {
        try{
            String result = "";
            String line;
            while ((line = file.readLine()) != null){
                if (line.contains("-->")) {
                    String[] sliced = line.split(" ");
                    ArrayList<String> slicedList = new ArrayList<>(Arrays.asList(sliced));

                    String[] start = slicedList.get(0).split(":|\\,");
                    String[] stop = slicedList.get(2).split(":|\\,");

                    Time startTime = new Time(start[0], start[1], start[2], start[3]);
                    Time stopTime = new Time(stop[0], stop[1], stop[2], stop[3]);

                    if (indicator.equals("+")) {
                        startTime.increaseTime(hour, minute, second, miliSecond);
                        stopTime.increaseTime(hour, minute, second, miliSecond);
                    } else if (indicator.equals("-")){
                        startTime.decreaseTime(hour, minute, second, miliSecond);
                        stopTime.decreaseTime(hour, minute, second, miliSecond);
                    }

                    line = String.format("%s --> %s", startTime.toStringWithComma(), stopTime.toStringWithComma());

                }
                result += (line + "\r\n");
            }
            output = result;
            reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public File download(){
        File tempFile = new File("temp.txt");
        try {
            PrintWriter out = new PrintWriter(tempFile);
            out.println(output);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tempFile;
    }
}
