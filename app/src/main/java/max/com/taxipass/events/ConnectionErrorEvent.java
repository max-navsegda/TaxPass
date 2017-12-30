package max.com.taxipass.events;



public class ConnectionErrorEvent {
    private boolean isShow;

    public ConnectionErrorEvent(boolean isShow) {
        this.isShow = isShow;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
