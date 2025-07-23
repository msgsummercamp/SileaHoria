package org.example.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

@Service
public class PDFGeneratorService {
    private static final Logger logger = LoggerFactory.getLogger(PDFGeneratorService.class);

    @Value("${pdfPath}")
    private String pdfPath;

    public void writeTextToExistingPDF(String name, String content) throws IOException, DocumentException {
        String oldContent = readPDFContent(name);

        System.gc();

        String fullPath = pdfPath + java.io.File.separator + name;
        java.io.File oldFile = new java.io.File(fullPath);
        if (oldFile.exists()) {
            boolean deleted = oldFile.delete();
            logger.info("Deleted existing PDF file: {}, success: {}", name, deleted);
        }

        System.gc();

        writeTextToPDF(name, oldContent + "\n" + content);
    }

    public String readPDFContent(String name) throws IOException {
        String fullPath = pdfPath + java.io.File.separator + name;

        PdfReader reader = null;
        StringBuilder content = new StringBuilder();

        try {
            reader = new PdfReader(fullPath);
            int numberOfPages = reader.getNumberOfPages();

            for (int i = 1; i <= numberOfPages; i++) {
                String pageText = PdfTextExtractor.getTextFromPage(reader, i);
                content.append(pageText);
                if (i < numberOfPages) {
                    content.append("\n\n");
                }
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        return content.toString();
    }

    public void writeTextToPDF(String name, String content) throws FileNotFoundException, DocumentException {
        Document document = openDocument(name);

        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Paragraph paragraph = new Paragraph(content, font);
        document.add(paragraph);

        document.close();
    }

    private Document openDocument(String name) throws FileNotFoundException, DocumentException {
        String fullPath = pdfPath + java.io.File.separator + name;

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(fullPath));
        document.open();
        return document;
    }

    public void writeImageToPDF(String name, String imageName) throws IOException, DocumentException, URISyntaxException {
        Document document = openDocument(name);

        URL resourceUrl = this.getClass().getClassLoader().getResource(imageName);
        if (resourceUrl == null) {
            document.close();
            logger.error("Image file '{}' not found in resources directory. Make sure the file is in src/main/resources/", imageName);
            throw new FileNotFoundException(String.format("Image file '%s' not found in resources directory. Make sure the file is in src/main/resources/", imageName));
        }

        try {
            Path path = Paths.get(resourceUrl.toURI());
            Image image = Image.getInstance(path.toAbsolutePath().toString());
            document.add(image);
        } catch (Exception e) {
            document.close();
            logger.error("Failed to add image '{}' to PDF '{}': {}", imageName, name, e.getMessage(), e);
            throw e;
        }

        document.close();
    }
}