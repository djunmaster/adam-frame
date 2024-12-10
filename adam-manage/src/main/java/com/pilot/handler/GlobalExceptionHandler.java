package com.pilot.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
    @RestControllerAdvice：
    标识这是一个全局的异常处理类。
    它的作用范围是整个 Spring MVC 应用，所有由控制器抛出的异常都会被它捕获。
    和 @ControllerAdvice 类似，但结合了 @ResponseBody 的功能，默认会将返回值序列化为 JSON。
*/
@RestControllerAdvice
public class GlobalExceptionHandler {
    /*
        @ExceptionHandler(MethodArgumentNotValidException.class)：
        表示这个方法专门处理 MethodArgumentNotValidException 类型的异常

        MethodArgumentNotValidException 是 Spring 中常见的参数校验异常
        通常在使用 Bean Validation（如 @Valid 或 @Validated）校验失败时抛出。
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // 返回错误信息
        return ResponseEntity.badRequest().body("参数校验失败: " + ex.getMessage());
    }
}
