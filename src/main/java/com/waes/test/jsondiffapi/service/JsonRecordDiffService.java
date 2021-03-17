package com.waes.test.jsondiffapi.service;

import com.waes.test.model.DiffResultDTO;

public interface JsonRecordDiffService {

    DiffResultDTO getJsonDiffs(final String id);
}
