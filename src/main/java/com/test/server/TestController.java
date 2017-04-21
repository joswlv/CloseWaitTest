package com.test.server;

import org.apache.catalina.connector.ClientAbortException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Created by Jo_seungwan on 2017. 4. 17..
 */
@RestController
@Slf4j
public class TestController {
    @RequestMapping("delayRequest")
    public Result delayRequest(@RequestParam(defaultValue = "short") String delay) {
        Result result = new Result();
        result.msg = "";

        if (!delay.equals("short")) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 1000; i++) {
                result.msg += "data row\n";
            }
        } else {
            result.msg = "short end";
        }

        return result;
    }

    @ExceptionHandler(ClientAbortException.class)
    public void handleClientAbortException2(Exception e){
        if (e instanceof ClientAbortException){
            log.error("ClientAbortException!!!!");
        }
    }

    @ExceptionHandler(Exception.class)
    public String handleClientAbortException(Exception e) {
            log.error("Exception : ");
            e.printStackTrace();
        return "error";
    }

    @Data
    private class Result {
        private String msg;
    }
}

