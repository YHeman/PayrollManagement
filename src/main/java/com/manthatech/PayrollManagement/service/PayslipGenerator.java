package com.manthatech.PayrollManagement.service;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.BorderCollapsePropertyValue;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.manthatech.PayrollManagement.DTOS.PayslipAllowance;
import com.manthatech.PayrollManagement.DTOS.PayslipDeduction;
import com.manthatech.PayrollManagement.DTOS.PayslipDetails;
import com.manthatech.PayrollManagement.model.Allowance;
import com.manthatech.PayrollManagement.model.Deduction;
import com.manthatech.PayrollManagement.utils.IndianCurrencyConverter;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.Map;
import java.util.UUID;

@Service
public class PayslipGenerator {

    public void generatePayslip(PayslipDetails payslipDetails) throws Exception {
        String uniqueId = UUID.randomUUID().toString();
        String dest = "payslip" + uniqueId + ".pdf";
        PdfWriter writer = new PdfWriter(new FileOutputStream(dest));
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);


        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        document.setFont(font).setFontSize(11);

        addHeader(document);
        addEmployeeDetails(document, payslipDetails);
        addSalaryDetails(document, payslipDetails);
        addNetSalary(document, payslipDetails);
        addFooter(document);

        document.close();
    }

    private void addHeader(Document document) {
        Paragraph header = new Paragraph("Company Details")
                .setFontSize(14)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER);
        document.add(header);

        Paragraph subheader = new Paragraph("Payslip")
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(subheader);
    }

    private void addEmployeeDetails(Document document, PayslipDetails payslipDetails) {
        Table employeeTable = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth()
                .setMarginTop(20)
                .setMarginBottom(10);

        // Left column
        Table leftColumn = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
        addEmployeeDetail(leftColumn, "Employee No", String.valueOf(payslipDetails.getEmployeeId()));
        addEmployeeDetail(leftColumn, "Employee Name", payslipDetails.getEmployeeName());
        addEmployeeDetail(leftColumn, "Department", payslipDetails.getDepartment());
        addEmployeeDetail(leftColumn, "Designation", payslipDetails.getDesignation());

        // Right column
        Table rightColumn = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
        addEmployeeDetail(rightColumn, "PAN Number", payslipDetails.getEmployeeSensitiveInfo().getPan());
        addEmployeeDetail(rightColumn, "Account Number", payslipDetails.getEmployeeSensitiveInfo().getBankAccountNumber());
        addEmployeeDetail(rightColumn, "IFSC Code", payslipDetails.getEmployeeSensitiveInfo().getIfscCode());
        addEmployeeDetail(rightColumn, "Date of Joining", payslipDetails.getHireDate().toString());

        employeeTable.addCell(new Cell().add(leftColumn).setBorder(com.itextpdf.layout.borders.Border.NO_BORDER));
        employeeTable.addCell(new Cell().add(rightColumn).setBorder(com.itextpdf.layout.borders.Border.NO_BORDER));

        document.add(employeeTable);
    }

    private void addEmployeeDetail(Table table, String label, String value) {
        table.addCell(createCell(label, "", TextAlignment.LEFT).setBold());
        table.addCell(createCell("", value, TextAlignment.LEFT));
    }

    private void addSalaryDetails(Document document, PayslipDetails payslipDetails) {
        Table salaryTable = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth()
                .setMarginTop(10)
                .setMarginBottom(10);

        Cell earningsCell = createHeaderCell("Earnings", TextAlignment.LEFT);
        Cell deductionsCell = createHeaderCell("Deductions", TextAlignment.LEFT);

        salaryTable.addCell(earningsCell);
        salaryTable.addCell(deductionsCell);

        Table earningsTable = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
        Table deductionsTable = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();

        addEarnings(earningsTable, payslipDetails);
        addDeductions(deductionsTable, payslipDetails);

        salaryTable.addCell(new Cell().add(earningsTable).setBorder(com.itextpdf.layout.borders.Border.NO_BORDER));
        salaryTable.addCell(new Cell().add(deductionsTable).setBorder(com.itextpdf.layout.borders.Border.NO_BORDER));

        document.add(salaryTable);
    }

    private void addEarnings(Table earningsTable, PayslipDetails payslipDetails) {
        addSalaryComponent(earningsTable, "Basic Salary", payslipDetails.getBasicSalary().doubleValue());
        for (Map.Entry<String, PayslipAllowance> entry : payslipDetails.getAllowances().entrySet()) {
            addSalaryComponent(earningsTable, entry.getKey(), entry.getValue().getAmount().doubleValue());
        }
        addSalaryComponent(earningsTable, "Total Earnings", payslipDetails.getSalaryCalculationResult().getGrossSalary().doubleValue());
    }

    private void addDeductions(Table deductionsTable, PayslipDetails payslipDetails) {
        for (Map.Entry<String, PayslipDeduction> entry : payslipDetails.getDeductions().entrySet()) {
            addSalaryComponent(deductionsTable, entry.getKey(), entry.getValue().getAmount().doubleValue());
        }
        addSalaryComponent(deductionsTable, "Total Deductions", payslipDetails.getSalaryCalculationResult().getDeductions().doubleValue());
    }

    private void addSalaryComponent(Table table, String label, double value) {
        table.addCell(createCell(label, "", TextAlignment.LEFT));
        table.addCell(createCell("", formatValue(value), TextAlignment.RIGHT));
    }

    private void addNetSalary(Document document, PayslipDetails payslipDetails) {
        double netAmount = payslipDetails.getSalaryCalculationResult().getNetSalary().doubleValue();
        Paragraph netSalaryParagraph = new Paragraph("Net Salary: " + formatValue(netAmount))
                .setFontSize(12)
                .setBold()
                .setMarginTop(10);
        document.add(netSalaryParagraph);

        Paragraph netSalaryInWords = new Paragraph("Net Salary in words: " + numberToWords((int) netAmount))
                .setFontSize(11)
                .setMarginTop(5);
        document.add(netSalaryInWords);
    }

    private void addFooter(Document document) {
        Paragraph footerNote = new Paragraph("THIS IS A SYSTEM GENERATED PAY SLIP, DOES NOT REQUIRE ANY SIGNATURE AND/OR COMPANY SEAL")
                .setFontSize(8)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginTop(20);
        document.add(footerNote);
    }

    private Cell createCell(String label, String value, TextAlignment alignment) {
        Cell cell = new Cell().add(new Paragraph(label + (value.isEmpty() ? "" : ": " + value)))
                .setBorder(com.itextpdf.layout.borders.Border.NO_BORDER)
                .setPadding(5)
                .setTextAlignment(alignment);
        return cell;
    }

    private Cell createHeaderCell(String header, TextAlignment alignment) {
        Cell cell = new Cell().add(new Paragraph(header))
                .setBold()
                .setTextAlignment(alignment)
                .setPadding(5)
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setBorder(com.itextpdf.layout.borders.Border.NO_BORDER);
        return cell;
    }

    private String formatValue(Double value) {
        return String.format("%.2f", value);
    }

    private String numberToWords(double amount) {
        return IndianCurrencyConverter.convertToWords(amount);
    }
}





//PdfFont font = PdfFontFactory.createFont("C:\\Users\\Uday Kiran Reddy\\Downloads\\Open_Sans\\static\\OpenSans-Regular.ttf", PdfEncodings.IDENTITY_H);
//        document.setFont(font).setFontSize(11);