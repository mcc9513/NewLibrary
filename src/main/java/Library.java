import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;

public class Library {
    private List<Book> books = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    // Add a book to the library
    public void addBook(Book book) {
        books.add(book);
    }

    // Remove a book by title
    public void removeBook(String title) {
        books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
    }

    // Find all books published in a specific year
    public List<Book> findBooksByYear(int year) {
        return books.stream()
                .filter(book -> book.getPublicationYear() == year)
                .collect(Collectors.toList());
    }

    // Find all books by a specific author
    public List<Book> findBooksByAuthor(String author) {
        return books.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    // Find the book with the most pages
    public Optional<Book> findBookWithMostPages() {
        return books.stream()
                .max(Comparator.comparingInt(Book::getPages));
    }

    // Find all books with more than n pages
    public List<Book> findBooksWithMoreThanNPages(int n) {
        return books.stream()
                .filter(book -> book.getPages() > n)
                .collect(Collectors.toList());
    }

    // Print all book titles in the library, sorted alphabetically
    public void printAllBookTitles() {
        books.stream()
                .map(Book::getTitle)
                .sorted()
                .forEach(System.out::println);
    }

    // Find all books in a specific category
    public List<Book> findBooksByCategory(String category) {
        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    // Loan out a book
    public boolean loanBook(String title, User user) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && !book.isOnLoan()) {
                book.setOnLoan(true);
                book.setLoanDate(LocalDate.now());
                user.loanBook(book);
                return true;
            }
        }
        return false;
    }

    // Return a book
    public boolean returnBook(String title, User user) {
        Optional<Book> bookToReturn = user.getBooksOnLoan().stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst();

        if (bookToReturn.isPresent()) {
            Book book = bookToReturn.get();
            book.setOnLoan(false);
            user.returnBook(book);
            return true;
        }

        return false;
    }

    // Register a new user
    public void registerUser(User user) {
        users.add(user);
    }

    // Calculate late fees for a user
    public double calculateLateFees(User user) {
        double fee = 0.0;
        for (Book book : user.getBooksOnLoan()) {
            if (book.isOnLoan()) {
                long daysOnLoan = LocalDate.now().toEpochDay() - book.getLoanDate().toEpochDay();
                if (daysOnLoan > 14) {
                    fee += (daysOnLoan - 14) * 0.5; // $0.5 per day late fee
                }
            }
        }
        return fee;
    }
}

