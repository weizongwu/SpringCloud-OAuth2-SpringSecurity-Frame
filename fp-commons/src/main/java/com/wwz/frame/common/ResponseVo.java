package com.wwz.frame.common;
/**  
* @Description 统一返回格式
* @Author wwz
* @Date 2019/07/26 
* @Param
* @Return   
*/ 
public class ResponseVo<T> {
    private Integer code;
    private String message;
    private T data;

    public ResponseVo() {
        this(200, "", null);
    }

    public ResponseVo(T data) {
        this();
        if (data instanceof Throwable) {
            Throwable exception = (Throwable) data;
            this.code = -1;
            this.message = exception.getMessage();
        } else {
            this.data = data;
        }

    }

    public ResponseVo(Integer code, String message) {
        this(code, message, null);
    }

    public ResponseVo(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ResponseVo)) {
            return false;
        } else {
            ResponseVo<?> other = (ResponseVo) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47:
                {
                    Object this$code = this.getCode();
                    Object other$code = other.getCode();
                    if (this$code == null) {
                        if (other$code == null) {
                            break label47;
                        }
                    } else if (this$code.equals(other$code)) {
                        break label47;
                    }

                    return false;
                }

                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof ResponseVo;
    }

    public int hashCode() {
        int result = 1;
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    public String toString() {
        return "ResponseVo(code=" + this.getCode() + ", message=" + this.getMessage() + ", data=" + this.getData() + ")";
    }
}
