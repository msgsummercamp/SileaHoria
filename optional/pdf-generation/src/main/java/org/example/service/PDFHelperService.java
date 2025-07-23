package org.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class PDFHelperService {
    private static final Logger logger = LoggerFactory.getLogger(PDFHelperService.class);
    private final PDFGeneratorService pdfGeneratorService;

    public PDFHelperService(@Autowired PDFGeneratorService pdfGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
    }

    public void createNewPDF(Scanner sc) {
        try {
            logger.info("\n--- Creating New PDF ---");
            logger.info("Enter the name of the PDF file:");
            String name = sc.nextLine() + ".pdf";
            logger.info("Enter the content for the PDF:");
            String content = sc.nextLine();

            logger.info("Do you want to add an image to the PDF? (y/n):");
            String addImage = sc.nextLine().trim().toLowerCase();

            pdfGeneratorService.writeTextToPDF(name, content);

            if (addImage.equals("y") || addImage.equals("yes")) {
                logger.info("Enter the name of the Image file:");
                String imageName = sc.nextLine();
                pdfGeneratorService.writeImageToPDF(name, imageName);
                logger.info("PDF '{}' created successfully with text and image!", name);
            } else {
                logger.info("PDF '{}' created successfully with text only!", name);
            }

        } catch (Exception e) {
            logger.error("An error occurred while creating the PDF: {}", e.getMessage(), e);
        }
    }

    public void addTextToExistingPDF(Scanner sc) {
        try {
            logger.info("\n--- Adding Text to Existing PDF ---");
            logger.info("Enter the name of the existing PDF file (without .pdf extension):");
            String existingFileName = sc.nextLine() + ".pdf";
            logger.info("Enter the text to add:");
            String newText = sc.nextLine();

            pdfGeneratorService.writeTextToExistingPDF(existingFileName, newText);

            logger.info("Text added successfully!");

        } catch (Exception e) {
            logger.error("An error occurred while adding text to PDF: {}", e.getMessage(), e);
        }
    }
}
