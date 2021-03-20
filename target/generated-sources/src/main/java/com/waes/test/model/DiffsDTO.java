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
 * Actual differencies between the JSONs
 */
@ApiModel(description = "Actual differencies between the JSONs")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-20T20:07:28.133828Z[Etc/UTC]")
public class DiffsDTO   {
  @JsonProperty("offset")
  private Integer offset = null;

  @JsonProperty("length")
  private Integer length = null;

  public DiffsDTO offset(Integer offset) {
    this.offset = offset;
    return this;
  }

  /**
   * Get offset
   * @return offset
  **/
  @ApiModelProperty(example = "1", required = true, value = "")
      @NotNull

    public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  public DiffsDTO length(Integer length) {
    this.length = length;
    return this;
  }

  /**
   * Get length
   * @return length
  **/
  @ApiModelProperty(example = "2", required = true, value = "")
      @NotNull

    public Integer getLength() {
    return length;
  }

  public void setLength(Integer length) {
    this.length = length;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DiffsDTO diffsDTO = (DiffsDTO) o;
    return Objects.equals(this.offset, diffsDTO.offset) &&
        Objects.equals(this.length, diffsDTO.length);
  }

  @Override
  public int hashCode() {
    return Objects.hash(offset, length);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DiffsDTO {\n");
    
    sb.append("    offset: ").append(toIndentedString(offset)).append("\n");
    sb.append("    length: ").append(toIndentedString(length)).append("\n");
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
