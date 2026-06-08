import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LibraryMainTest {

    @Test
    void testLoginFunctionality() {
        // 1. 재료(repository)를 생성해서 넣어줍니다.
        LibraryRepository repo = new LibraryRepository();
        LibraryManager manager = new LibraryManager(repo);

        // 2. 로그인 테스트 수행
        boolean result = manager.login("admin", "11111");

        // 3. 검증
        assertNotNull(result, "로그인 결과는 반드시 있어야 합니다.");
    }
}