package com.waes.test.model;

import java.util.Objects;
import io.swagger.annotations.ApiModel;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Enum to demonstrate if the JSONs are equal or not
 */
public enum DiffResultEnum {
  EQUAL("EQUAL"),
    NOT_EQUAL("NOT_EQUAL"),
    DIFFERENT_LENGHT("DIFFERENT_LENGHT");

  private String value;

  DiffResultEnum(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static DiffResultEnum fromValue(String text) {
    for (DiffResultEnum b : DiffResultEnum.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
