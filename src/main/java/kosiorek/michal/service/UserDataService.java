package kosiorek.michal.service;

import kosiorek.michal.exceptions.ExceptionCode;
import kosiorek.michal.exceptions.MyException;
import org.apache.commons.validator.routines.EmailValidator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class UserDataService {

    private Scanner sc = new Scanner(System.in);

    int getInt(String message) {
        System.out.println(message);

        String data = sc.nextLine();
        if (!data.matches("\\d+")) {
            throw new MyException(ExceptionCode.OTHER, "INT VALUE IS NOT CORRECT: " + data);
        }

        return Integer.parseInt(data);
    }

    public String getString(String message, String regex) {
        System.out.println(message);

        String data = sc.nextLine();
        if (regex != null && !regex.isEmpty() && !data.matches(regex)) {
            throw new MyException(ExceptionCode.OTHER, "STRING VALUE IS NOT CORRECT: " + data);
        }

        return data;
    }

    public BigDecimal getBigDecimal(String message) {
        System.out.println(message);

        String data = sc.nextLine();
        if (!data.matches("^([1-9]+\\d*(?:\\.\\d{2})?)*")) {
            throw new MyException(ExceptionCode.OTHER, "BIG DECIMAL VALUE IS NOT CORRECT: " + data);
        }

        return BigDecimal.valueOf(Double.parseDouble(data));
    }

    public String getEmail(String message) {
        System.out.println(message);

        String data = sc.nextLine();
        EmailValidator emailValidator = EmailValidator.getInstance();
        if (!emailValidator.isValid(data)) {
            throw new MyException(ExceptionCode.OTHER, "EMAIL VALUE IS NOT CORRECT: " + data);
        }

        return data;
    }

    public LocalDate getDate(String message) {
        System.out.println(message);
        String regex = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))";

        String data = sc.nextLine();
        if (!data.matches(regex)) {
            throw new MyException(ExceptionCode.OTHER, "DATE VALUE IS NOT CORRECT: " + data);
        }
        LocalDate date = LocalDate.parse(data);
        return date;
    }

    public boolean getBoolean(String message) {
        System.out.println(message + " [y/n?]");
        return sc.nextLine().charAt(0) == 'y';
    }

    public void close() {
        if (sc != null) {
            sc.close();
            sc = null;
        }
    }

}
