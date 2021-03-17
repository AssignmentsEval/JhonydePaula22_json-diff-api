package com.waes.test.jsondiffapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waes.test.jsondiffapi.exception.InvalidDataException;
import com.waes.test.jsondiffapi.exception.InvalidParametersException;
import com.waes.test.jsondiffapi.exception.JsonDataNotPresentException;
import com.waes.test.jsondiffapi.exception.JsonRecordNotFoundException;
import com.waes.test.jsondiffapi.service.JsonRecordDiffService;
import com.waes.test.jsondiffapi.service.JsonRecordService;
import com.waes.test.model.ErrorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.waes.test.jsondiffapi.TestConstants.DIFF_JSON_PATH;
import static com.waes.test.jsondiffapi.constants.ExceptionConstants.DEFAULT_ERROR_MESSAGE;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RestExceptionHandlerControllerTest {

    private static final String MESSAGE = "exception";
    private ObjectMapper mapper;
    private JsonRecordService jsonRecordService;
    private JsonRecordDiffService jsonRecordDiffService;
    private JsonOperationsController controller;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mapper = new ObjectMapper();
        jsonRecordService = mock(JsonRecordService.class);
        jsonRecordDiffService = mock(JsonRecordDiffService.class);
        controller = new JsonOperationsController(jsonRecordService, jsonRecordDiffService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new RestExceptionHandlerController())
                .build();
    }

    @Test
    void should_return_ErrorDTO_and_bad_request_when_InvalidDataException_happens() throws Exception {
        final ErrorDTO expected = new ErrorDTO().message(MESSAGE);

        doThrow(new InvalidDataException(MESSAGE)).when(jsonRecordDiffService).getJsonDiffs("id");

        mockMvc.perform(get(format(DIFF_JSON_PATH, "id"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult -> assertEquals(expected, mapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorDTO.class)));
    }

    @Test
    void should_return_ErrorDTO_and_bad_request_when_InvalidParametersException_happens() throws Exception {
        final ErrorDTO expected = new ErrorDTO().message(MESSAGE);

        doThrow(new InvalidParametersException(MESSAGE)).when(jsonRecordDiffService).getJsonDiffs("id");

        mockMvc.perform(get(format(DIFF_JSON_PATH, "id"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult -> assertEquals(expected, mapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorDTO.class)));
    }

    @Test
    void should_return_ErrorDTO_and_bad_request_when_JsonDataNotPresentException_happens() throws Exception {
        final ErrorDTO expected = new ErrorDTO().message(MESSAGE);

        doThrow(new JsonDataNotPresentException(MESSAGE)).when(jsonRecordDiffService).getJsonDiffs("id");

        mockMvc.perform(get(format(DIFF_JSON_PATH, "id"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult -> assertEquals(expected, mapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorDTO.class)));
    }

    @Test
    void should_return_ErrorDTO_and_bad_request_when_JsonRecordNotFoundException_happens() throws Exception {
        final ErrorDTO expected = new ErrorDTO().message(MESSAGE);

        doThrow(new JsonRecordNotFoundException(MESSAGE)).when(jsonRecordDiffService).getJsonDiffs("id");

        mockMvc.perform(get(format(DIFF_JSON_PATH, "id"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult -> assertEquals(expected, mapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorDTO.class)));
    }

    @Test
    void should_return_InternalServerError_when_an_unexpected_error_happens() throws Exception {
        final ErrorDTO expected = new ErrorDTO().message(DEFAULT_ERROR_MESSAGE);

        doThrow(new NullPointerException("Unexpected exception")).when(jsonRecordDiffService).getJsonDiffs("id");

        mockMvc.perform(get(format(DIFF_JSON_PATH, "id"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(mvcResult -> assertEquals(expected, mapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorDTO.class)));
    }
}