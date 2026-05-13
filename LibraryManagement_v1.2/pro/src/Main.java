import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        searchBooks();
    }

    public static void searchBooks() {
        System.out.println("\n[도서 검색]");
        System.out.print("검색할 도서 제목 입력: ");
        String keyword = sc.nextLine();

        String sql = "SELECT book_id, title, author, is_available FROM books WHERE title LIKE ?";

        try {
            Connection conn = DBconn.getConnection();

            if (conn == null) {
                System.out.println("[오류] DB 연결에 실패했습니다.");
                return;
            }

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + keyword + "%");

            ResultSet rs = pstmt.executeQuery();

            System.out.println("==================================================");
            System.out.println(" [books 테이블 검색 결과]");
            System.out.printf(" %-5s | %-25s | %-15s | %-10s \n",
                    "ID", "제목", "저자", "상태");
            System.out.println("--------------------------------------------------");

            boolean found = false;

            while (rs.next()) {
                found = true;

                int bookId = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                boolean available = rs.getBoolean("is_available");

                String status = available ? "대출 가능" : "대출 중";

                System.out.printf(" %-5d | %-25s | %-15s | %-10s \n",
                        bookId, title, author, status);
            }

            if (!found) {
                System.out.println("검색 결과가 없습니다.");
            }

            System.out.println("==================================================");

            rs.close();
            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("[오류] books 테이블 검색 실패");
            e.printStackTrace();
        }
    }
}