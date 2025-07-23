package org.example;

import org.example.service.PDFHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class Main implements CommandLineRunner {
    private final PDFHelperService pdfHelperService;

    public Main(@Autowired PDFHelperService pdfHelperService) {
        this.pdfHelperService = pdfHelperService;
    }

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
                        pdfHelperService.createNewPDF(sc);
                        break;
                    case "2":
                        pdfHelperService.addTextToExistingPDF(sc);
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
}