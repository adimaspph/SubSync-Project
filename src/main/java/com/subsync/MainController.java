package com.subsync;

import com.subsync.DataBase.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


@Controller
public class MainController {

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @PostMapping("/")
    public Object getFile(Model model, @RequestParam("file") MultipartFile file, @RequestParam(value = "removeEffect", required = false) String removeEffect,
                          @RequestParam("indicator") String indicator, @RequestParam(value = "hour", required = false) String h, @RequestParam(value = "minute", required = false) String m,
                          @RequestParam(value = "second", required = false) String s, @RequestParam(value = "miliSecond", required = false) String ms) {


        SubSync subSync = new SRTFile(file);

        int hh = 0, mm = 0, ss = 0, msms = 0;

        if (!h.equals("")){
            hh = Integer.parseInt(h);
        }
        if (!m.equals("")){
            mm = Integer.parseInt(m);
        }
        if (!s.equals("")){
            ss = Integer.parseInt(s);
        }
        if (!ms.equals("")){
            msms = Integer.parseInt(ms);
        }


        try {
            subSync.sync(indicator, hh, mm, ss, msms);
        } catch (InvalidTimeException e) {
            return "timeError";
        }
        //TODO: Implement Remove
        if (removeEffect != null){
            subSync.removeTag();
        }


        File outputFile = subSync.download();
        InputStreamResource resource = null;
        try {
            resource = new InputStreamResource(new FileInputStream(outputFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Disposition", String.format("attachment; filename=\"SubSync-%s\"", subSync.getFileName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity<Object>
                responseEntity = ResponseEntity.ok().headers(headers).contentLength(outputFile.length()).contentType(
                MediaType.parseMediaType("application/txt")).body(resource);


        return responseEntity;
    }

}
