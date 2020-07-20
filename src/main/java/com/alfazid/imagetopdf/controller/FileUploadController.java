package com.alfazid.imagetopdf.controller;

import com.alfazid.imagetopdf.convert.ConvertImageToPdf;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by www.alfaz.id mail : mohirwanh@gmail.com on 09/08/19.
 */

@Controller
public class FileUploadController {
    private static String UPLOADED_FOLDER = "/Users/cigist/uploads/";
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("uploadingFiles") MultipartFile[] uploadingFiles,
                                   RedirectAttributes redirectAttributes) {

        if (uploadingFiles.length<1) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }
        boolean resultSave = saveImage(uploadingFiles,redirectAttributes);
        if(resultSave){
            redirectAttributes.addFlashAttribute("message", "You successfully uploaded");
            List<String> apth = new ArrayList<>();
            for(MultipartFile uploadedFile : uploadingFiles) {
                apth.add(uploadedFile.getOriginalFilename());
            }
             convertImageTiSinglePdf(apth);
        }else{
            redirectAttributes.addFlashAttribute("message", "You failed uploaded");
        }
        return "redirect:/";
    }

    public boolean saveImage(MultipartFile[] files,RedirectAttributes redirectAttributes){
        try {
            for(MultipartFile uploadedFile : files) {
                File file = new File(UPLOADED_FOLDER + uploadedFile.getOriginalFilename());
                uploadedFile.transferTo(file);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean convertImageTiSinglePdf(List<String>  imageName){
        try {
            ConvertImageToPdf convertImageToPdf = new ConvertImageToPdf();
            boolean result =convertImageToPdf.diImageToPdf(UPLOADED_FOLDER,"/Users/cigist/Downloads/",imageName);

            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }

}
