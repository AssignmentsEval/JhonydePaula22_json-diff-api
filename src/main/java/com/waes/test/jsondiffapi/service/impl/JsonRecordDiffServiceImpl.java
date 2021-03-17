package com.waes.test.jsondiffapi.service.impl;

import com.waes.test.jsondiffapi.exception.JsonDataNotPresentException;
import com.waes.test.jsondiffapi.exception.JsonRecordNotFoundException;
import com.waes.test.jsondiffapi.model.entity.JsonRecord;
import com.waes.test.jsondiffapi.repository.JsonRecordRepository;
import com.waes.test.jsondiffapi.service.JsonRecordDiffService;
import com.waes.test.model.DiffResultDTO;
import com.waes.test.model.DiffsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.waes.test.jsondiffapi.constants.AppLogMessagesConstants.DIFFS_RESULT_DTO_LOG_MESSAGE;
import static com.waes.test.jsondiffapi.constants.AppLogMessagesConstants.JSON_RECORD_RETRIEVED_LOG_MESSAGE;
import static com.waes.test.jsondiffapi.constants.ExceptionConstants.LEFT_JSON_NOT_PRESENT_EXCEPTION_MESSAGE;
import static com.waes.test.jsondiffapi.constants.ExceptionConstants.RIGHT_JSON_NOT_PRESENT_EXCEPTION_MESSAGE;
import static com.waes.test.model.DiffResultEnum.DIFFERENT_LENGHT;
import static com.waes.test.model.DiffResultEnum.EQUAL;
import static com.waes.test.model.DiffResultEnum.NOT_EQUAL;
import static java.lang.Integer.MIN_VALUE;

/**
 * JsonRecordDiffServiceImpl implements {@link JsonRecordDiffService}
 * Performs operations related to finding the differences in two Jsons Data stored in a {@link JsonRecord} into the database
 *
 * @author Jonathan de Paula
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class JsonRecordDiffServiceImpl implements JsonRecordDiffService {

    private final JsonRecordRepository jsonRecordRepository;

    /**
     * Perform the operations needed to find the differences.
     *
     * @param id {@link String}
     * @return {@link DiffResultDTO}
     */
    @Override
    public DiffResultDTO getJsonDiffs(final String id) {
        final JsonRecord jsonRecord = getJsonRecord(id);
        validateIfJsonDataIsPresentOnBothSides(jsonRecord);
        return generateDiffResultDTO(jsonRecord);
    }

    /**
     * Search for a {@link JsonRecord} on the data base.
     *
     * @param id {@link String}
     * @return {@link JsonRecord}
     */
    private JsonRecord getJsonRecord(final String id) {
        JsonRecord jsonRecord = jsonRecordRepository.findById(id).orElseThrow(() -> new JsonRecordNotFoundException("No Json record was found. Try again with a valid ID"));
        log.info(JSON_RECORD_RETRIEVED_LOG_MESSAGE, jsonRecord);
        return jsonRecord;
    }

    /**
     * Validate if a {@link JsonRecord} contains Json Data in both sides.
     *
     * @param jsonRecord {@link JsonRecord}
     */
    private void validateIfJsonDataIsPresentOnBothSides(final JsonRecord jsonRecord) {
        if (jsonRecord.isLeftSideNull()) {
            throw new JsonDataNotPresentException(LEFT_JSON_NOT_PRESENT_EXCEPTION_MESSAGE);
        }
        if (jsonRecord.isRightSideNull()) {
            throw new JsonDataNotPresentException(RIGHT_JSON_NOT_PRESENT_EXCEPTION_MESSAGE);
        }
    }

    /**
     * Generate the {@link DiffResultDTO} accordingly with the data present into the {@link JsonRecord}
     *
     * @param jsonRecord {@link JsonRecord}
     * @return {@link DiffResultDTO}
     */
    private DiffResultDTO generateDiffResultDTO(final JsonRecord jsonRecord) {
        final DiffResultDTO diffResultDTO;

        if (jsonRecord.jsonsAreDifferentInLength()) {
            diffResultDTO = new DiffResultDTO().result(DIFFERENT_LENGHT);
        } else {
            List<DiffsDTO> diffsDTOList = findDiffs(jsonRecord);
            diffResultDTO = diffsDTOList.isEmpty() ?
                    new DiffResultDTO().result(EQUAL) :
                    new DiffResultDTO().result(NOT_EQUAL).diffs(findDiffs(jsonRecord));
        }
        log.info(DIFFS_RESULT_DTO_LOG_MESSAGE, diffResultDTO, jsonRecord);
        return diffResultDTO;
    }

    /**
     * This method contains the logic that find the differences between to Jsons that are stored in a {@link JsonRecord}.
     *
     * @param jsonRecord {@link JsonRecord}
     * @return {@link List<DiffsDTO>}
     */
    private List<DiffsDTO> findDiffs(final JsonRecord jsonRecord) {
        final List<DiffsDTO> diffsDTOList = new ArrayList<>();

        int offset = MIN_VALUE;
        int lenght = 1;

        // This will be the index that will control the iteration over the bytes on the arrays
        for (int i = 0; i < jsonRecord.getLeft_json().length; i++) {
            // if in the same index, both bytes are equals
            if (jsonRecord.getLeft_json()[i] == jsonRecord.getRight_json()[i]) {
                // than, if we are not counting the length of a previous mismatched byte we continue to the next index.
                if (offset < 0) {
                    continue;
                }
                //if we are counting we add the offset and length to the DiffDTOlist, reset the offset and length, than we continue to the next index.
                diffsDTOList.add(new DiffsDTO().offset(offset).length(lenght));
                offset = MIN_VALUE;
                lenght = 1;
                continue;
            }

            // if in the same index, both bytes are not equals and we are not counting the length yet, we set the offset as the current index and continue to the next index to calculate the length of the differences
            if (offset < 0) {
                offset = i;
                continue;
            }

            // if we are already counting we just increase the length of the differences.
            lenght++;
        }

        return diffsDTOList;
    }
}
