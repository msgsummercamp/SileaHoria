package org.example;

import org.example.service.PDFGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private PDFGeneratorService pdfGeneratorService;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner sc = new Scanner(System.in);

        try (sc) {
            while (true) {
                System.out.println("\n=== PDF Generator Menu ===");
                System.out.println("1. Create new PDF with text and image");
                System.out.println("2. Add text to existing PDF");
                System.out.println("3. Quit");
                System.out.print("Choose an option (1-3): ");

                String choice = sc.nextLine().trim();

                switch (choice) {
                    case "1":
                        createNewPDF(sc);
                        break;
                    case "2":
                        addTextToExistingPDF(sc);
                        break;
                    case "3":
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid option. Please choose 1, 2, or 3.");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    private void createNewPDF(Scanner sc) {
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

    private void addTextToExistingPDF(Scanner sc) {
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