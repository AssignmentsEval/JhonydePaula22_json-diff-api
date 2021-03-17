package com.waes.test.jsondiffapi.repository;

import com.waes.test.jsondiffapi.model.entity.JsonRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * JsonRecordRepository is responsible to implements the crud operations to persist and retrieve data about {@link JsonRecord}
 *
 * @author Jonathan de Paula
 */
@Repository
public interface JsonRecordRepository extends CrudRepository<JsonRecord, String> {
}
