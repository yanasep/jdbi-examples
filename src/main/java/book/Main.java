package book;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:bookdb");
        jdbi.installPlugin(new SqlObjectPlugin());

        Book book1 = new Book("9780553328257", "Sir Arthur Conan Doyle", "The Complete Sherlock Holmes", Book.Format.PAPERBACK, "Bantam Doubleday Dell Publishing Group Inc", LocalDate.of(1998, 10, 6), 2, true);
        Book book2 = new Book("9781509874514", "David Baldacci", "Walk the Wire", Book.Format.HARDBACK, "Pan MacMillan", LocalDate.of(2020, 4, 16), 432, true);
        Book book3 = new Book("9780008327057", "Henry Firth", "BISH BASH BOSH!", Book.Format.HARDBACK, "HarperCollins Publishers", LocalDate.of(2019, 4, 4), 288, true);

        try (Handle handle = jdbi.open()) {
            BookDao dao = handle.attach(BookDao.class);
            dao.createTables();
            dao.insert(book1);
            dao.insert(book2);
            dao.insert(book3);
            System.out.println(dao.find(book1.getIsbn13()).get());
            dao.delete(book1);
            dao.findAll().stream().forEach(System.out::println);
        }
    }
}
