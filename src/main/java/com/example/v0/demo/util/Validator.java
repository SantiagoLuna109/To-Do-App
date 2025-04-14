package com.example.v0.demo.util;

import com.example.v0.demo.exception.ValidationException;
import com.example.v0.demo.model.ToDo;
import com.example.v0.demo.service.ToDoServiceImpl;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class Validator {
    public static void isNotEmpty(String text){
        if(StringUtils.isEmpty(text)) {
            throw new ValidationException("This is a mandatory field");
        }
        if(StringUtils.length(text)>120){
            throw new ValidationException("Please do not exced 120 characteres");
        }
    }
    public static void isNotEmptyPriority(Integer priority){
        if(Objects.isNull(priority) || priority < 0){
            throw new ValidationException("The priority must be one of the options");
        }
    }
}
