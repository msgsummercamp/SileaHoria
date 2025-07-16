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

        System.out.println("Enter the name of the PDF file:");
        String name = sc.nextLine() + ".pdf";

        System.out.println("Enter the content for the PDF:");
        String content = sc.nextLine();

        System.out.println("Enter the name of the Image file:");
        String imageName = sc.nextLine();

        try {
            pdfGeneratorService.writeTextToPDF(name, content);
//            pdfGeneratorService.writeImageToPDF(name, imageName);
        } catch (Exception e) {
            System.err.println("An error occurred while generating the PDF: " + e.getMessage());
        } finally {
            sc.close();
            System.out.println("PDF generation completed.");
        }
    }
}