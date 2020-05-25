package com.subsync.DataBase;


import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public abstract class SubSync {
    protected String fileName;
    protected BufferedReader file;
    protected String output;

    public SubSync(MultipartFile file) {
        try {
            this.fileName = file.getOriginalFilename();
            InputStream is = file.getInputStream();
            this.file = new BufferedReader(new InputStreamReader(is));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SubSync(BufferedReader buff){
        this.fileName = "OutTest";
        file = buff;
    }

    public void reset(){
        Reader inputString = new StringReader(output);
        file = new BufferedReader(inputString);
    }

    public String getFileName(){
        return fileName;
    }

    public BufferedReader getFile() {
        return file;
    }

    public abstract void removeTag();

    public abstract void sync(String indicator, int hour, int minute, int second, int miliSecond) throws InvalidTimeException;

    public abstract File download();
}
