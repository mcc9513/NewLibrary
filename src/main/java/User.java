import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private int libraryCardNumber;
    private List<Book> booksOnLoan = new ArrayList<>();

    public User(String name, int libraryCardNumber) {
        this.name = name;
        this.libraryCardNumber = libraryCardNumber;
    }

    public String getName() { return name; }
    public int getLibraryCardNumber() { return libraryCardNumber; }
    public List<Book> getBooksOnLoan() { return booksOnLoan; }

    public void loanBook(Book book) {
        booksOnLoan.add(book);
    }

    public void returnBook(Book book) {
        booksOnLoan.remove(book);
    }
}
