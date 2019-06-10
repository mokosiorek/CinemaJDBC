package kosiorek.michal.exceptions;

public enum ExceptionCode {

    OTHER ("UNCLASSIFIED EXCEPTION"),
    VALIDATION ("VALIDATION EXCEPTION"),
    JSON_PARSE ("JSON PARSE EXCEPTION");

    private String description;

    ExceptionCode(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
