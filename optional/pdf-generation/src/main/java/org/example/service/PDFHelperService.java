package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class PDFHelperService {
    private final PDFGeneratorService pdfGeneratorService;

    public PDFHelperService(@Autowired PDFGeneratorService pdfGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
    }

    public void createNewPDF(Scanner sc) {
        try {
            System.out.println("\n--- Creating New PDF ---");
            System.out.println("Enter the name of the PDF file:");
            String name = sc.nextLine() + ".pdf";
            System.out.println("Enter the content for the PDF:");
            String content = sc.nextLine();

            System.out.println("Do you want to add an image to the PDF? (y/n):");
            String addImage = sc.nextLine().trim().toLowerCase();

            pdfGeneratorService.writeTextToPDF(name, content);

            if (addImage.equals("y") || addImage.equals("yes")) {
                System.out.println("Enter the name of the Image file:");
                String imageName = sc.nextLine();
                pdfGeneratorService.writeImageToPDF(name, imageName);
                System.out.println("PDF '" + name + "' created successfully with text and image!");
            } else {
                System.out.println("PDF '" + name + "' created successfully with text only!");
            }

        } catch (Exception e) {
            System.err.println("An error occurred while creating the PDF: " + e.getMessage());
        }
    }

    public void addTextToExistingPDF(Scanner sc) {
        try {
            System.out.println("\n--- Adding Text to Existing PDF ---");
            System.out.println("Enter the name of the existing PDF file (without .pdf extension):");
            String existingFileName = sc.nextLine() + ".pdf";
            System.out.println("Enter the text to add:");
            String newText = sc.nextLine();

            pdfGeneratorService.writeTextToExistingPDF(existingFileName, newText);

            System.out.println("Text added successfully!");

        } catch (Exception e) {
            System.err.println("An error occurred while adding text to PDF: " + e.getMessage());
        }
    }
}
