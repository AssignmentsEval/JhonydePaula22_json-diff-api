package com.waes.test.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Erro Object
 */
@ApiModel(description = "Erro Object")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-20T20:07:28.133828Z[Etc/UTC]")
public class ErrorDTO   {
  @JsonProperty("message")
  private String message = null;

  public ErrorDTO message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Description of possible errors that ocurred while performing some action
   * @return message
  **/
  @ApiModelProperty(example = "Error description", required = true, value = "Description of possible errors that ocurred while performing some action")
      @NotNull

    public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorDTO errorDTO = (ErrorDTO) o;
    return Objects.equals(this.message, errorDTO.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorDTO {\n");
    
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
