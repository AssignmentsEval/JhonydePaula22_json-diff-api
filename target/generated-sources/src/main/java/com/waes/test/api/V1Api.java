/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.18).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.waes.test.api;

import com.waes.test.model.DiffResultDTO;
import com.waes.test.model.ErrorDTO;
import com.waes.test.model.SideEnum;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CookieValue;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-20T20:07:28.133828Z[Etc/UTC]")
@Api(value = "v1", description = "the v1 API")
public interface V1Api {

    @ApiOperation(value = "Method to handle POST requests. Persists a JSON in a record, to later be diff-ed", nickname = "saveJson", notes = "", tags={ "Input Data Endpoint", })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad Request", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorDTO.class) })
    @RequestMapping(value = "/v1/diff/{id}/{side}",
        produces = { "application/json" }, 
        consumes = { "text/plain", "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> saveJson(@ApiParam(value = "" ,required=true )  @Valid @RequestBody byte[] body
,@ApiParam(value = "ID of the record",required=true) @PathVariable("id") String id
,@ApiParam(value = "Side that the JSON must be saved on the record",required=true) @PathVariable("side") SideEnum side
);


    @ApiOperation(value = "Method to handle GET requests. Returns a record composed by two JSONs and demonstrate diff result based on the provided ID", nickname = "verifyDiff", notes = "", response = DiffResultDTO.class, tags={ "Retrieve Data Endpoint", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = DiffResultDTO.class),
        @ApiResponse(code = 400, message = "Bad Request", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorDTO.class) })
    @RequestMapping(value = "/v1/diff/{id}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<DiffResultDTO> verifyDiff(@ApiParam(value = "ID of the record",required=true) @PathVariable("id") String id
);

}
