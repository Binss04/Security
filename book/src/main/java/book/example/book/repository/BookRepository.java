package book.example.book.repository;

import book.example.book.enity.Book;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

@Repository
public class BookRepository {

    private final JdbcTemplate jdbcTemplate;

    public BookRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Book> findAllBooks() {
        // Khởi tạo SimpleJdbcCall trong phương thức
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("ALL_BOOK") // Tên procedure
                .withCatalogName("BOOK_PKG")   // Tên package
                .declareParameters(new SqlOutParameter("p_out_cursor", OracleTypes.CURSOR, new BookRowMapper()));

        // Gọi stored procedure
        Map<String, Object> result = simpleJdbcCall.execute();
        // Lấy danh sách sách từ con trỏ trả về
        return (List<Book>) result.get("p_out_cursor");
    }

    private static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book book = new Book();
            book.setId(rs.getLong("ID"));
            book.setTitle(rs.getString("TITLE"));
            book.setAuthor(rs.getString("AUTHOR"));
            book.setGenre(rs.getString("GENRE"));
            book.setLanguage(rs.getString("LANGUAGE"));
            book.setPagecount(rs.getInt("PAGECOUNT"));
            book.setPrice(rs.getInt("PRICE"));
            book.setPublish_date(rs.getDate("PUBLISH_DATE"));
            book.setPublisher(rs.getString("PUBLISHER"));
            book.setSynopsis(rs.getString("SYNOPSIS"));
            return book;
        }
    }

    public void insertBook(String author, String genre, String language, int pagecount, double price, Date publish_date, String publisher, String synopsis, String title) {
        String sql = "{call BOOK_PKG.INSERT_BOOK(?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        jdbcTemplate.update(sql,
                new Object[]{author, genre, language, pagecount, price, publish_date, publisher, synopsis, title},
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.DOUBLE, Types.DATE, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR});
    }

    public void deleteBook(Long id) {
        String sql = "{call BOOK_PKG.DELETE_BOOK(?)}";
        jdbcTemplate.update(sql, id);
    }

    public void updateBook(Long id, String author, String genre, String language, int pagecount, double price, Date publishDate, String publisher, String synopsis, String title) {
        String sql = "{call BOOK_PKG.UPDATE_BOOK(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        jdbcTemplate.update(sql, id, author, genre, language, pagecount, price, publishDate, publisher, synopsis, title);
    }

    public Map<String, Object> searchBooks(String author, String genre, String language, Date startDate, Date endDate, int pageNo, int pageSize) {
        author = (author != null && !author.trim().isEmpty()) ? author.trim() : null;
        genre = (genre != null && !genre.trim().isEmpty()) ? genre.trim() : null;
        language = (language != null && !language.trim().isEmpty()) ? language.trim() : null;
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("BOOK_PKG") // Tên package trong database
                .withProcedureName("SEARCH_BOOK") // Tên procedure trong database
                .declareParameters(
                        new SqlParameter("b_author", OracleTypes.VARCHAR),
                        new SqlParameter("b_genre", OracleTypes.VARCHAR),
                        new SqlParameter("b_language", OracleTypes.VARCHAR),
                        new SqlParameter("b_start_date", OracleTypes.DATE),
                        new SqlParameter("b_end_date", OracleTypes.DATE),
                        new SqlParameter("b_page_no", OracleTypes.NUMBER),
                        new SqlParameter("b_page_size", OracleTypes.NUMBER),
                        new SqlOutParameter("b_total", OracleTypes.NUMBER),
                        new SqlOutParameter("b_out_cursor", OracleTypes.CURSOR, (rs, rowNum) -> {
                            Map<String, Object> bookDetails = new HashMap<>();
                            bookDetails.put("id", rs.getLong("id"));
                            bookDetails.put("author", rs.getString("author"));
                            bookDetails.put("genre", rs.getString("genre"));
                            bookDetails.put("language", rs.getString("language"));
                            bookDetails.put("pagecount", rs.getInt("pagecount"));
                            bookDetails.put("price", rs.getDouble("price"));
                            bookDetails.put("publish_date", rs.getDate("publish_date"));
                            bookDetails.put("publisher", rs.getString("publisher"));
                            bookDetails.put("synopsis", rs.getString("synopsis"));
                            bookDetails.put("title", rs.getString("title"));
                            return bookDetails;
                        })
                );

        // Thực thi procedure
        Map<String, Object> result = jdbcCall.execute(author, genre, language, startDate, endDate, pageNo, pageSize);

        // Trả về kết quả
        return result;
    }
}
