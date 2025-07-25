package org.example;

import lombok.AllArgsConstructor;
import org.example.service.PDFHelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
@AllArgsConstructor
public class Main implements CommandLineRunner {
    private final PDFHelperService pdfHelperService;
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner sc = new Scanner(System.in);

        try (sc) {
            while (true) {
                logger.info("\n=== PDF Generator Menu ===");
                logger.info("1. Create new PDF with text and image");
                logger.info("2. Add text to existing PDF");
                logger.info("3. Quit");
                logger.info("Choose an option (1-3): ");

                String choice = sc.nextLine().trim();

                switch (choice) {
                    case "1":
                        pdfHelperService.createNewPDF(sc);
                        break;
                    case "2":
                        pdfHelperService.addTextToExistingPDF(sc);
                        break;
                    case "3":
                        logger.info("Goodbye!");
                        return;
                    default:
                        logger.warn("Invalid option. Please choose 1, 2, or 3.");
                }
            }
        } catch (Exception e) {
            logger.error("An error occurred: {}", e.getMessage());
        }
    }
}