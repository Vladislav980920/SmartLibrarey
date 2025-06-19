package api;


import java.util.List;

public class LoanController {
    private final LoanService loanService;
    public Loan createLoan(@RequestParam Long bookId, @RequestParam Long userId) {
        return loanService.createLoan(bookId, userId);
    }
    public List<Loan> getLoansByUser(@PathVariable Long userId) {
        return loanService.findByUserId(userId);
    }
    public Loan returnBook(@PathVariable Long loanId) {
        return loanService.returnBook(loanId);
    }
}
