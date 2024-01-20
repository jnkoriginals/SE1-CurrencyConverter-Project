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

    public void ConvertToPDF(String baseCurrency, double baseAmount, Map<String, Double> results) throws FileNotFoundException {

        DecimalFormat df = new DecimalFormat("##.00");
        final String FILENAME = "CurrencyConverter";
        int fileCounter = 1;

        File fileOutput = new File(FILENAME + ".pdf");
        if (fileOutput.exists()) {
            while (fileOutput.exists()) {
                fileCounter++;
                fileOutput = new File(FILENAME + fileCounter + ".pdf");
            }
        }

        String fileName;
        if (fileCounter != 1) {
            fileName = FILENAME + fileCounter + ".pdf";
        } else {
            fileName = FILENAME + ".pdf";
        }

        PdfWriter PdfWriter = new PdfWriter(fileName);
        PdfDocument pdfDocument = new PdfDocument(PdfWriter);
        pdfDocument.addNewPage();
        Document document = new Document(pdfDocument);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        Paragraph date = new Paragraph("Exchange rates as of " + dtf.format(now));
        date.setTextAlignment(TextAlignment.CENTER);
        document.add(date);

        Table table = new Table(3);
        table.addCell("Base currency");
        table.addCell("Target currency");
        table.addCell("Exchange rate");
        table.addCell(df.format(baseAmount) + " " + baseCurrency);

        int i = 1;
        for (String k : results.keySet()) {
            double exchangeRate = results.get(k) / baseAmount;

            table.addCell(df.format(results.get(k)) + " " + k);
            table.addCell(df.format(exchangeRate));
            if (results.size() == 1) {
                break;
            }
            if (i < results.size()) {
                table.addCell(" ");
            }
            i++;
        }
        table.setHorizontalAlignment(CENTER);
        document.add(table);
        document.close();
    }

}
