package com.manthatech.PayrollManagement.controller;

/*
@RestController
public class PayslipController {

    @Autowired
    private PayslipService payslipService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SalaryRepository salaryRepository;

    @GetMapping("/payslip/{employeeId}/{salaryId}")
    public ResponseEntity<byte[]> generatePayslip(@PathVariable Long employeeId, @PathVariable Long salaryId) {
        try {
            Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));
            Salary salary = salaryRepository.findById(salaryId).orElseThrow(() -> new RuntimeException("Salary not found"));

            byte[] pdfBytes = payslipService.generatePayslip(employee, salary);

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_TYPE, "application/pdf");
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=payslip.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
*/