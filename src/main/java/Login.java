import java.util.Scanner;
import java.util.regex.Pattern;

public class Login {

    private String storedUsername;
    private String storedPassword;

    // Validates that the username contains an underscore and is 5 characters or less.
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    // Validates that the password is at least 8 characters long and contains
    // at least one uppercase letter, one digit, and one special character.
    public boolean checkPasswordComplexity(String password) {
        if (password.length() < 8) {
            return false;
        }
        boolean hasUpper   = password.chars().anyMatch(Character::isUpperCase);
        boolean hasDigit   = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.chars().anyMatch(c -> !Character.isLetterOrDigit(c));
        return hasUpper && hasDigit && hasSpecial;
    }

    // Validates that the cell number starts with an international dialling code (e.g. +27)
    // and is no more than 12 characters in total.
    // Regex adapted from the E.164 international telephone numbering format:
    // https://www.itu.int/rec/T-REC-E.164/en
    public boolean checkCellPhoneNumber(String cellNumber) {
        String regex = "^\\+[0-9]{1,11}$";
        return Pattern.matches(regex, cellNumber) && cellNumber.length() <= 12;
    }

    // Validates each registration field in order and returns the appropriate
    // success or error message. Stores credentials only when all three fields pass.
    // Returns "Password successfully captured." when username and password are valid
    // but no cell number is provided, and "Cell number successfully captured." when
    // all three fields pass.
    public String registerUser(String username, String password, String cellNumber) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted, please ensure that your username "
                    + "contains an underscore and is no more than 5 characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted, please ensure that the password "
                    + "contains at least 8 characters, a capital letter, a number and a "
                    + "special character.";
        }
        if (cellNumber == null || cellNumber.isEmpty()) {
            return "Password successfully captured.";
        }
        if (!checkCellPhoneNumber(cellNumber)) {
            return "Cell phone number is incorrectly formatted or does not contain an "
                    + "international code.";
        }
        storedUsername = username;
        storedPassword = password;
        return "Cell number successfully captured.";
    }

    // Returns true if the provided credentials match the stored username and password.
    public boolean loginUser(String username, String password) {
        return username.equals(storedUsername) && password.equals(storedPassword);
    }

    // Returns a personalised welcome message on successful login, or an error message
    // if the credentials do not match.
    public String returnLoginStatus(String username, String password,
                                    String firstName, String lastName) {
        if (loginUser(username, password)) {
            return "Welcome " + firstName + " " + lastName
                    + " it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        System.out.println("=== Chat App Registration ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter cell number (e.g. +27838968976): ");
        String cellNumber = scanner.nextLine();

        String registrationResult = login.registerUser(username, password, cellNumber);
        System.out.println(registrationResult);

        if ("Cell number successfully captured.".equals(registrationResult)) {
            System.out.println("\n=== Login ===");
            System.out.print("Enter first name: ");
            String firstName = scanner.nextLine();

            System.out.print("Enter last name: ");
            String lastName = scanner.nextLine();

            System.out.print("Enter username: ");
            String loginUsername = scanner.nextLine();

            System.out.print("Enter password: ");
            String loginPassword = scanner.nextLine();

            System.out.println(login.returnLoginStatus(loginUsername, loginPassword,
                    firstName, lastName));
        }

        scanner.close();
    }
}
