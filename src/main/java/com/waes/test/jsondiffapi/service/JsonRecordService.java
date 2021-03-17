package com.waes.test.jsondiffapi.service;

import com.waes.test.model.SideEnum;

public interface JsonRecordService {

    void save(final String id, final SideEnum side, final byte[] encodedData);
}
