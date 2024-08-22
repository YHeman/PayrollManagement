package com.manthatech.PayrollManagement.service;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.borders.Border;
import com.manthatech.PayrollManagement.model.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
/*
@Service
public class PayslipService {

    private static final String PDF_DIRECTORY = "C:\\Users\\Uday Kiran Reddy\\Desktop\\PaySlips";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static final DeviceRgb DARK_BLUE = new DeviceRgb(0, 51, 102);
    private static final DeviceRgb LIGHT_BLUE = new DeviceRgb(235, 241, 255);
    private static final DeviceRgb HEADER_COLOR = new DeviceRgb(58, 132, 195);
    private static final DeviceRgb FOOTER_COLOR = new DeviceRgb(128, 128, 128);

    public byte[] generatePayslip(Employee employee, Salary salary) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try (PdfWriter writer = new PdfWriter(byteArrayOutputStream);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            document.setFont(font);
            document.setMargins(30, 25, 20, 25);

            addHeader(document);
            addEmployeeDetails(document, employee, boldFont);
            addSalaryDetails(document, salary, boldFont);
            addEarningsDeductionsTable(document, salary, boldFont);
            addNetPayable(document, salary, boldFont);
            addFooter(document, "XYZ Corp, 123 Business St, Business City, Country");

        }

        saveToFile(employee, byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }

    private void addHeader(Document document) throws IOException {
        InputStream logoStream = getClass().getResourceAsStream("/static/logo.png");
        if (logoStream == null) {
            throw new IOException("Logo image not found");
        }
        byte[] imageBytes = logoStream.readAllBytes();
        ImageData imageData = ImageDataFactory.create(imageBytes);
        Image logo = new Image(imageData);
        logo.setWidth(50);
        logo.setHeight(50);

        Table headerTable = new Table(UnitValue.createPercentArray(new float[]{70, 30}));
        headerTable.setWidth(UnitValue.createPercentValue(100));

        Cell logoCell = new Cell().add(logo);
        logoCell.setBorder(Border.NO_BORDER);
        headerTable.addCell(logoCell);

        Cell companyCell = new Cell().add(new Paragraph("Red Circle Solutions")
                .setFontSize(14)
                .setBold()
                .setFontColor(HEADER_COLOR))
                .setTextAlignment(TextAlignment.RIGHT);
        companyCell.setBorder(Border.NO_BORDER);

        companyCell.add(new Paragraph("Silicon Valley, Block 2")).setFontSize(12);
        companyCell.add(new Paragraph("Hyderabad")).setFontSize(12);

        headerTable.addCell(companyCell);


        document.add(headerTable);
        document.add(new Paragraph("\n"));
    }

    private void addEmployeeDetails(Document document, Employee employee, PdfFont boldFont) {
        Table employeeTable = new Table(UnitValue.createPercentArray(new float[]{50, 50}));
        employeeTable.setWidth(UnitValue.createPercentValue(100));

        addEmployeeDetailRow(employeeTable, "Employee Name", employee.getFirstName() + " " + employee.getLastName(), boldFont);
        addEmployeeDetailRow(employeeTable, "Designation", employee.getJob().getJobTitle(), boldFont);
        addEmployeeDetailRow(employeeTable, "Date of Joining", employee.getHireDate().format(DATE_FORMATTER), boldFont);
        addEmployeeDetailRow(employeeTable, "Pay Period", "July 2024", boldFont);
        addEmployeeDetailRow(employeeTable, "Pay Date", "30-06-2024", boldFont);
        addEmployeeDetailRow(employeeTable, "PF A/C Number", "AA/AAA/0000000/000/0000000", boldFont);
        addEmployeeDetailRow(employeeTable, "UAN Number", "101010101010", boldFont);

        employeeTable.setMarginBottom(10);
        document.add(employeeTable);
    }

    private void addEmployeeDetailRow(Table table, String label, String value, PdfFont boldFont) {
        Cell labelCell = new Cell().add(new Paragraph(label + ":").setFontSize(12).setFont(boldFont).setFontColor(DARK_BLUE));
        Cell valueCell = new Cell().add(new Paragraph(value).setFontSize(12));
        labelCell.setBorder(Border.NO_BORDER);
        valueCell.setBorder(Border.NO_BORDER);
        table.addCell(labelCell);
        table.addCell(valueCell);
    }

    private void addSalaryDetails(Document document, Salary salary, PdfFont boldFont) {
        Table salaryTable = new Table(UnitValue.createPercentArray(new float[]{50, 50}));
        salaryTable.setWidth(UnitValue.createPercentValue(100));

        addSalaryDetailRow(salaryTable, "Paid Days", "28", boldFont);
        addSalaryDetailRow(salaryTable, "LOP Days", "3", boldFont);

        salaryTable.setMarginBottom(10);
        document.add(salaryTable);
    }

    private void addSalaryDetailRow(Table table, String label, String value, PdfFont boldFont) {
        Cell labelCell = new Cell().add(new Paragraph(label).setFontSize(10).setFont(boldFont));
        Cell valueCell = new Cell().add(new Paragraph(value).setFontSize(10));
        labelCell.setBorder(Border.NO_BORDER);
        valueCell.setBorder(Border.NO_BORDER);
        table.addCell(labelCell);
        table.addCell(valueCell);
    }

    private void addEarningsDeductionsTable(Document document, Salary salary, PdfFont boldFont) {
        Table table = new Table(UnitValue.createPercentArray(new float[]{25, 25, 25, 25}));
        table.setWidth(UnitValue.createPercentValue(100));

        addTableHeader(table, boldFont, "EARNINGS", "AMOUNT", "DEDUCTIONS", "AMOUNT");

        addEarningsDeductions(table, salary);

        table.setMarginBottom(10);
        document.add(table);
    }

    private void addTableHeader(Table table, PdfFont boldFont, String... headers) {
        for (String header : headers) {
            Cell cell = new Cell().add(new Paragraph(header).setFontSize(10).setFont(boldFont));
            cell.setBackgroundColor(LIGHT_BLUE);
            cell.setFontColor(DARK_BLUE);
            table.addHeaderCell(cell);
        }
    }

    private void addEarningsDeductions(Table table, Salary salary) {
        // Add earnings
        addTableRow(table, "Basic", formatCurrency(salary.getBasicSalary()));
        for (EmployeeAllowance allowance : salary.getCustomAllowances()) {
            addTableRow(table, allowance.getAllowance().getName(), formatCurrency(allowance.getAmount()));
        }

        // Add deductions
        int deductionRowIndex = 0;
        for (EmployeeDeduction deduction : salary.getCustomDeductions()) {
            if (deductionRowIndex == 0) {
                table.addCell(new Cell().setBorder(Border.NO_BORDER));
                table.addCell(new Cell().setBorder(Border.NO_BORDER));
            }
            addTableRow(table, deduction.getDeduction().getName(), formatCurrency(deduction.getAmount()));
            deductionRowIndex++;
        }

        // Fill remaining cells if necessary
        while (deductionRowIndex < salary.getCustomAllowances().size()) {
            table.addCell(new Cell().setBorder(Border.NO_BORDER));
            table.addCell(new Cell().setBorder(Border.NO_BORDER));
            deductionRowIndex++;
        }
    }

    private void addTableRow(Table table, String label, String value) {
        Cell labelCell = new Cell().add(new Paragraph(label).setFontSize(10));
        Cell valueCell = new Cell().add(new Paragraph(value).setFontSize(10));
        labelCell.setBorder(Border.NO_BORDER);
        valueCell.setBorder(Border.NO_BORDER);
        table.addCell(labelCell);
        table.addCell(valueCell);
    }


    private void addNetPayable(Document document, Salary salary, PdfFont boldFont) {
        Table netPayTable = new Table(UnitValue.createPercentArray(new float[]{70, 30}));
        netPayTable.setWidth(UnitValue.createPercentValue(100));

        Cell labelCell = new Cell().add(new Paragraph("Net Payable")
                .setFontSize(12)
                .setFont(boldFont)
                .setFontColor(ColorConstants.WHITE));
        Cell valueCell = new Cell().add(new Paragraph(formatCurrency(salary.getNetSalary()))
                .setFontSize(12)
                .setFont(boldFont)
                .setFontColor(ColorConstants.WHITE));

        labelCell.setBackgroundColor(DARK_BLUE);
        valueCell.setBackgroundColor(DARK_BLUE);
        labelCell.setTextAlignment(TextAlignment.CENTER);
        valueCell.setTextAlignment(TextAlignment.CENTER);
        labelCell.setBorder(new SolidBorder(ColorConstants.WHITE, 1));
        valueCell.setBorder(new SolidBorder(ColorConstants.WHITE, 1));

        netPayTable.addCell(labelCell);
        netPayTable.addCell(valueCell);

        document.add(netPayTable);
    }


    private void addFooter(Document document, String font) {
        Paragraph footer = new Paragraph("-This is a system-generated payslip-")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(8)
                .setFontColor(ColorConstants.GRAY);
        document.add(footer);
    }

    private String formatCurrency(BigDecimal amount) {
        return "₹" + String.format("%.2f", amount);
    }

    private void saveToFile(Employee employee, ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        Path directory = Paths.get(PDF_DIRECTORY);
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }

        String fileName = String.format("payslip_%s_%s.pdf", employee.getEmployeeId(),
                employee.getLastName().toLowerCase());
        Path filePath = directory.resolve(fileName);

        try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
            fos.write(byteArrayOutputStream.toByteArray());
        }
    }
}
*/
