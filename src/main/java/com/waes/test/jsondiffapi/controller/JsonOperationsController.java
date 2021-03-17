package com.waes.test.jsondiffapi.controller;

import com.waes.test.api.V1Api;
import com.waes.test.jsondiffapi.controller.propertyeditorsupport.SideEnumPropertyEditorSupport;
import com.waes.test.jsondiffapi.service.JsonRecordDiffService;
import com.waes.test.jsondiffapi.service.JsonRecordService;
import com.waes.test.model.DiffResultDTO;
import com.waes.test.model.SideEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * REST Controller to handle Json Record Endpoints
 *
 * @author Jonathan de Paula
 */
@RestController
@RequiredArgsConstructor
public class JsonOperationsController implements V1Api {

    private final JsonRecordService jsonRecordService;
    private final JsonRecordDiffService jsonRecordDiffService;

    /**
     * POST Method to save a Json Data into a Json Record in the database
     *
     * @param body Base64 Encoded String converted to byte[]
     * @param id   {@link String} Identification of the Json Record
     * @param side {@link SideEnum} Side where the Json Data must be stored
     * @return {@link ResponseEntity}
     */
    @Override
    public ResponseEntity<Void> saveJson(@Valid byte[] body, String id, SideEnum side) {
        jsonRecordService.save(id, side, body);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET Method to retrieve the differences between the Json Data inside a Json Record
     *
     * @param id {@link String}
     * @return {@link ResponseEntity} of {@link DiffResultDTO}
     */
    @Override
    public ResponseEntity<DiffResultDTO> verifyDiff(String id) {
        return ResponseEntity.ok(jsonRecordDiffService.getJsonDiffs(id));
    }

    /**
     * Registering PropertyEditor to handle SideEnum type
     * Used to customize the way that the requests will be sent to the controller.
     * In this particular case, changing how the SideEnum is read from the requests.
     *
     * @param webdataBinder {@link WebDataBinder}
     */
    @InitBinder
    private void initBinder(final WebDataBinder webdataBinder) {
        webdataBinder.registerCustomEditor(SideEnum.class, new SideEnumPropertyEditorSupport());
    }
}
