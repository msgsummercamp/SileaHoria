package org.example;

import org.example.service.PDFGeneratorService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the name of the PDF file:");
        String name = sc.nextLine();

        System.out.println("Enter the content for the PDF:");
        String content = sc.nextLine();

        try {
            PDFGeneratorService.writeToPDF(name, content);
        } catch (Exception e) {
            System.err.println("An error occurred while generating the PDF: " + e.getMessage());
        } finally {
            sc.close();
            System.out.println("PDF generation completed.");
        }
    }
}