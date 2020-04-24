package book;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(Book.class)
public interface BookDao {

    @SqlUpdate("""
            create table book (
                isbn13 varchar primary key,
                author varchar not null,
                title varchar not null,
                format varchar not null,
                publisher varchar not null,
                publicationDate date not null,
                pages integer not null,
                available boolean not null
            )
            """)
    void createTables();

    @SqlUpdate("insert into book values (:isbn13, :author, :title, :format, " +
            ":publisher, :publicationDate, :pages, :available)")
    void insert(@BindBean Book book);

    @SqlQuery("select * from book where isbn13 = :isbn13")
    Optional<Book> find(@Bind("isbn13") String isbn13);

    @SqlUpdate("delete from book where isbn13 = :isbn13")
    void delete(@BindBean Book book);

    @SqlQuery("select * from book order by publicationDate")
    List<Book> findAll();
}