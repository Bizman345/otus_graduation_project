package ru.wb.bot.exceptions;

/**
 * Исключение, когда возвращаемый ответ не OK
 */
public class NotOKStatusException extends Exception{

    public NotOKStatusException(String message) {
        super(message);
    }
}
