package api;


import com.smartlibrary.loan.model.Loan;
import com.smartlibrary.loan.service.LoanService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/loans")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoanController {
    private final LoanService loanService = new LoanService();

    @POST
    public Response createLoan(
            @QueryParam("bookId") Long bookId,
            @QueryParam("userId") Long userId) {

        Loan created = loanService.createLoan(bookId, userId);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @GET
    @Path("/user/{userId}")
    public List<Loan> getLoansByUser(@PathParam("userId") Long userId) {
        return loanService.findByUserId(userId);
    }

    @POST
    @Path("/return/{loanId}")
    public Loan returnBook(@PathParam("loanId") Long loanId) {
        return loanService.returnBook(loanId);
    }
}