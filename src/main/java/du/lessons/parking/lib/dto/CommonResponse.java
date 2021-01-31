package du.lessons.parking.lib.dto;

public class CommonResponse<T> {

    public void getSuccess(T body, String msg) {
        this.setBody(body);
        this.setMsg(msg);
        this.setSuccess(Boolean.TRUE);
    }

    public void getError(String msg) {
        this.setMsg(msg);
        this.setSuccess(Boolean.FALSE);
    }

    private T body;

    private Boolean success;

    private String msg;

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
