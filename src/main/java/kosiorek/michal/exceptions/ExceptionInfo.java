package kosiorek.michal.exceptions;

import java.time.LocalDateTime;

public class ExceptionInfo {

    private ExceptionCode exceptionCode;
    private String exceptionMessage;
    private LocalDateTime exceptionDateTime;

    public ExceptionInfo(ExceptionCode exceptionCode, String exceptionMessage) {
        this.exceptionCode = exceptionCode;
        this.exceptionMessage = exceptionMessage;
        this.exceptionDateTime = LocalDateTime.now();
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }

    public LocalDateTime getExceptionDateTime() {
        return exceptionDateTime;
    }
}
