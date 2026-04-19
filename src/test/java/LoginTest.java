import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    private Login login;

    @BeforeEach
    void setUp() {
        login = new Login();
    }

    // --- Username tests ---

    @Test
    void testValidUsernameReturnsTrue() {
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    void testValidUsernameRegistersSuccessfully() {
        assertEquals("Cell number successfully captured.",
                login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976"));
    }

    @Test
    void testInvalidUsernameReturnsFalse() {
        assertFalse(login.checkUserName("kyle!!!!!!!"));
    }

    @Test
    void testInvalidUsernameReturnsFormattingError() {
        assertEquals(
                "Username is not correctly formatted, please ensure that your username "
                        + "contains an underscore and is no more than 5 characters in length.",
                login.registerUser("kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976"));
    }

    // --- Password tests ---

    @Test
    void testValidPasswordReturnsTrue() {
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    void testValidPasswordCapturedMessage() {
        // Cell number is omitted to isolate the password-capture success message.
        assertEquals("Password successfully captured.",
                login.registerUser("kyl_1", "Ch&&sec@ke99!", ""));
    }

    @Test
    void testInvalidPasswordReturnsFalse() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    void testInvalidPasswordReturnsFormattingError() {
        assertEquals(
                "Password is not correctly formatted, please ensure that the password "
                        + "contains at least 8 characters, a capital letter, a number and a "
                        + "special character.",
                login.registerUser("kyl_1", "password", "+27838968976"));
    }

    // --- Cell number tests ---

    @Test
    void testValidCellNumberReturnsTrue() {
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    void testValidCellNumberCapturedMessage() {
        assertEquals("Cell number successfully captured.",
                login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976"));
    }

    @Test
    void testInvalidCellNumberReturnsFalse() {
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }

    @Test
    void testInvalidCellNumberReturnsFormattingError() {
        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an "
                        + "international code.",
                login.registerUser("kyl_1", "Ch&&sec@ke99!", "08966553"));
    }

    // --- Login tests ---

    @Test
    void testLoginSuccessReturnsTrue() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    void testLoginFailureReturnsFalse() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertFalse(login.loginUser("wrongUser", "wrongPass"));
    }

    @Test
    void testLoginStatusSuccessMessage() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Welcome John Doe it is great to see you again.",
                login.returnLoginStatus("kyl_1", "Ch&&sec@ke99!", "John", "Doe"));
    }

    @Test
    void testLoginStatusFailureMessage() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Username or password incorrect, please try again.",
                login.returnLoginStatus("wrongUser", "wrongPass", "John", "Doe"));
    }
}
