package com.kubepay.konics.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.kubepay.konics.model.BatchDto;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BatchFormValidator implements Validator {
  
  @Override
  public void validate(final Object target, final Errors errors) {
    
    final BatchDto batch = (BatchDto) target;
    
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "courseName", "10001", "Course Name is required!");
    
    log.info(batch.toString());
    
  }

  @Override
  public boolean supports(Class<?> clazz) {

    return BatchDto.class.equals(clazz);
  }

}
