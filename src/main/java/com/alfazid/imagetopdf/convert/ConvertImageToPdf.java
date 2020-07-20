package com.alfazid.imagetopdf.convert;

import javax.imageio.IIOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Created by www.alfaz.id mail : mohirwanh@gmail.com on 09/08/19.
 */
@EnableAsync
public class ConvertImageToPdf {
    public boolean diImageToPdf(String path,String outPath,List<String> imageUri){
        Document document = new Document();
        String fileName = UUID.randomUUID().toString()+".pdf";
        try {
            PdfWriter.getInstance(document, new FileOutputStream(new File(path, fileName)));
            document.open();

            for (String f : imageUri) {
                document.newPage();
                Image image = Image.getInstance(new File(path, f).getAbsolutePath());
                image.setAbsolutePosition(0, 0);
                image.setBorderWidth(0);
                image.scaleAbsoluteHeight(PageSize.A4.getHeight());
                image.scaleAbsoluteWidth(PageSize.A4.getWidth());
                document.add(image);
            }
            char[] animationChars = new char[]{'|', '/', '-', '\\'};

            for (int i = 0; i <= 100; i++) {
                System.out.print("Processing: " + i + "% " + animationChars[i % 4] + "\r");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            document.close();
            System.out.println("Processing: Done!             |\n");
            return true;
        }catch (Exception ex){
            System.out.println("Processing: Error!            |\n");
            ex.printStackTrace();
            return false;
        }
    }
}
