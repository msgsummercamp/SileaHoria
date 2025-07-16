package org.example.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PDFGeneratorService {
    public static void writeTextToPDF(String name, String content) throws FileNotFoundException, DocumentException {
        Document document = openDocument(name);

        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk(content, font);
        document.add(chunk);

        document.close();
    }

    public static void writeImageToPDF(String name, String imageName) throws IOException, DocumentException, URISyntaxException {
        Document document = openDocument(name);

        Path path = Paths.get(ClassLoader.getSystemResource(imageName).toURI());

        Image image = Image.getInstance(path.toAbsolutePath().toString());
        document.add(image);

        document.close();
    }

    private static Document openDocument(String name) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(name + ".pdf"));
        document.open();
        return document;
    }
}
