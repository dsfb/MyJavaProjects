package cinema.exception;

public class ExceptionJson {
    private String error;

    public ExceptionJson(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
