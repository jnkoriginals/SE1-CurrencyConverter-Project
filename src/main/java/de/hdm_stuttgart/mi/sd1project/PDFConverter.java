package de.hdm_stuttgart.mi.sd1project;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static com.itextpdf.layout.properties.HorizontalAlignment.CENTER;

public class PDFConverter {
    static String fileName; // File name for use in InterfaceClass
    /**
     * Generates a PDF document based on
     * the last conversion prompt.
     */
    public void ConvertToPDF(String baseCurrency, double baseAmount, Map<String, Double> results) throws FileNotFoundException {

        // Generating the documents file name based on possible previous exports.
        final String FILENAMEBASE = "CurrencyConverter";
        int fileCounter = 1;

        File fileOutput = new File(FILENAMEBASE + ".pdf");
        if (fileOutput.exists()) {
            while (fileOutput.exists()) {
                fileCounter++;
                fileOutput = new File(FILENAMEBASE + fileCounter + ".pdf");
            }
        }
        if (fileCounter != 1) {
            fileName = FILENAMEBASE + fileCounter + ".pdf";
        } else {
            fileName = FILENAMEBASE + ".pdf";
        }

        // Generating an empty PDF document.
        PdfWriter PdfWriter = new PdfWriter(fileName);
        PdfDocument pdfDocument = new PdfDocument(PdfWriter);
        Document document = new Document(pdfDocument);

        // Creating formatters.
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        DecimalFormat df = new DecimalFormat("##0.00");

        // Adding title and current date/time to the PDF document.
        Paragraph date = new Paragraph("Exchange rates as of " + dtf.format(now));
        date.setTextAlignment(TextAlignment.CENTER);
        document.add(date);

        // Generating a table containing results of previous conversion and adding it to the PDF.
        Table table = new Table(3);
        table.addCell("Base currency");
        table.addCell("Target currency");
        table.addCell("Exchange rate");

        for (String k : results.keySet()) {
            double exchangeRate = results.get(k) / baseAmount;

            table.addCell(df.format(baseAmount) + " " + baseCurrency);
            table.addCell(df.format(results.get(k)) + " " + k);
            table.addCell(df.format(exchangeRate));
            if (results.size() == 1) {
                break;
            }
        }
        table.setHorizontalAlignment(CENTER);
        document.add(table);

        document.close();
    }
}
