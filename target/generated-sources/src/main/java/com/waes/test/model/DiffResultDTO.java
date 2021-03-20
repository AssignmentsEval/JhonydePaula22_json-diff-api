package com.waes.test.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.waes.test.model.DiffResultEnum;
import com.waes.test.model.DiffsDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Object that represents the diffs between the JSONs, if there is any difference
 */
@ApiModel(description = "Object that represents the diffs between the JSONs, if there is any difference")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-20T20:07:28.133828Z[Etc/UTC]")
public class DiffResultDTO   {
  @JsonProperty("result")
  private DiffResultEnum result = null;

  @JsonProperty("diffs")
  @Valid
  private List<DiffsDTO> diffs = null;

  public DiffResultDTO result(DiffResultEnum result) {
    this.result = result;
    return this;
  }

  /**
   * Get result
   * @return result
  **/
  @ApiModelProperty(required = true, value = "")
      @NotNull

    @Valid
    public DiffResultEnum getResult() {
    return result;
  }

  public void setResult(DiffResultEnum result) {
    this.result = result;
  }

  public DiffResultDTO diffs(List<DiffsDTO> diffs) {
    this.diffs = diffs;
    return this;
  }

  public DiffResultDTO addDiffsItem(DiffsDTO diffsItem) {
    if (this.diffs == null) {
      this.diffs = new ArrayList<DiffsDTO>();
    }
    this.diffs.add(diffsItem);
    return this;
  }

  /**
   * Get diffs
   * @return diffs
  **/
  @ApiModelProperty(value = "")
      @Valid
    public List<DiffsDTO> getDiffs() {
    return diffs;
  }

  public void setDiffs(List<DiffsDTO> diffs) {
    this.diffs = diffs;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DiffResultDTO diffResultDTO = (DiffResultDTO) o;
    return Objects.equals(this.result, diffResultDTO.result) &&
        Objects.equals(this.diffs, diffResultDTO.diffs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(result, diffs);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DiffResultDTO {\n");
    
    sb.append("    result: ").append(toIndentedString(result)).append("\n");
    sb.append("    diffs: ").append(toIndentedString(diffs)).append("\n");
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
