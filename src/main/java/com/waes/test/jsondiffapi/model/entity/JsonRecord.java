package com.waes.test.jsondiffapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

import static java.util.Objects.isNull;

/**
 * JsonRecord is the entity persisted in the database whenever trying to find the differences between Jsons.
 *
 * @author Jonathan de Paula
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class JsonRecord {

    @Id
    private String id;
    private byte[] left_json;
    private byte[] right_json;

    /**
     * Return if the left side Json Data is null;
     *
     * @return {@link Boolean}
     */
    public boolean isLeftSideNull() {
        return isNull(this.left_json);
    }

    /**
     * Return if the right side Json Data is null;
     *
     * @return {@link Boolean}
     */
    public boolean isRightSideNull() {
        return isNull(this.right_json);
    }

    /**
     * Return if the both sides Json Data are not null;
     *
     * @return {@link Boolean}
     */
    public boolean bothJsonsAreNotNull() {
        return !isLeftSideNull() && !isRightSideNull();
    }

    /**
     * Return if the Json Data are different in length;
     *
     * @return {@link Boolean}
     */
    public boolean jsonsAreDifferentInLength() {
        return bothJsonsAreNotNull() && this.left_json.length != this.right_json.length;
    }
}
