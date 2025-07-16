package org.example.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PDFGeneratorService {
    @Value("${pdfPath}")
    private String pdfPath;

    public void writeTextToPDF(String name, String content) throws FileNotFoundException, DocumentException {
        Document document = openDocument(name);

        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk(content, font);
        document.add(chunk);

        document.close();
    }

    public void writeImageToPDF(String name, String imageName) throws IOException, DocumentException, URISyntaxException {
        Document document = openDocument(name);

        java.net.URL resourceUrl = this.getClass().getClassLoader().getResource(imageName);
        if (resourceUrl == null) {
            document.close();
            throw new FileNotFoundException("Image file '" + imageName + "' not found in resources directory. Make sure the file is in src/main/resources/");
        }

        try {
            Path path = Paths.get(resourceUrl.toURI());
            Image image = Image.getInstance(path.toAbsolutePath().toString());
            document.add(image);
        } catch (Exception e) {
            document.close();
            throw e;
        }

        document.close();
    }

    private Document openDocument(String name) throws FileNotFoundException, DocumentException {
        String fullPath = pdfPath + java.io.File.separator + name;

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(fullPath));
        document.open();
        return document;
    }
}