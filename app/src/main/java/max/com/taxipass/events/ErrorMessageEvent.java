package max.com.taxipass.events;



public class ErrorMessageEvent {
    private String message;

    public ErrorMessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
