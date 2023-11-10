package br.ifsul.quatroi.yesorno.exceptions;

public class YesOrNoException extends Exception {

    public YesOrNoException() {
    }

    public YesOrNoException(String message) {
        super(message);
    }

    public YesOrNoException(String message, Throwable cause) {
        super(message, cause);
    }

    public YesOrNoException(Throwable cause) {
        super(cause);
    }

    public YesOrNoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
