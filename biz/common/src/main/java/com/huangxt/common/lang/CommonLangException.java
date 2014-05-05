package com.huangxt.common.lang;

/**
 * common.lang包下面的通用异常
 */
public class CommonLangException extends Exception{

	private static final long serialVersionUID = 5609509906404124875L;

	/**
     * 构造一个空的异常.
     */
    public CommonLangException() {
        super();
    }

    /**
     * 构造一个异常, 指明异常的详细信息.
     *
     * @param message 详细信息
     */
    public CommonLangException(String message) {
        super(message);
    }

    /**
     * 构造一个异常, 指明引起这个异常的起因.
     *
     * @param cause 异常的起因
     */
    public CommonLangException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造一个异常, 指明引起这个异常的起因.
     *
     * @param message 详细信息
     * @param cause 异常的起因
     */
    public CommonLangException(String message, Throwable cause) {
        super(message, cause);
    }
}
