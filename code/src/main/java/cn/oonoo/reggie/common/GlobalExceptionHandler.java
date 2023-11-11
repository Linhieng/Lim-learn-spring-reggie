package cn.oonoo.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 */
@Slf4j
@ResponseBody
@ControllerAdvice(annotations = {RestController.class, Controller.class}) // 值应用在有添加 @RestController 或 @Controller 注解的类上
public class GlobalExceptionHandler {

    /**
     * 处理 sql 完整性约束校验异常。比如添加一个员工时，该员工的 username 已存在。
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        String errMessage = ex.getMessage();

        // 此处直接通过 errMessage 来提取异常信息，这种做法我个人觉得不太行。
        if (errMessage.contains("Duplicate entry")) {
            String[] s = errMessage.split(" ");
            return R.error(s[2] + " 已存在");
        }

        log.error(errMessage);

        return R.error("sql 完整性约束异常");
    }

    @ExceptionHandler(CustomException.class)
    public R<String> custonExceptionHandler(CustomException ex) {
        return R.error(ex.getMessage());
    }

}
