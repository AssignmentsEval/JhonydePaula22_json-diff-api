package com.waes.test.jsondiffapi.controller.propertyeditorsupport;

import com.waes.test.jsondiffapi.exception.InvalidParametersException;
import com.waes.test.model.SideEnum;

import java.beans.PropertyEditorSupport;

import static com.waes.test.jsondiffapi.constants.ExceptionConstants.INVALID_PARAMETER_EXCEPTION_MESSAGE;
import static java.lang.String.format;
import static java.util.Objects.isNull;

/**
 * PropertyEditor that converts String into SideEnum
 */
public class SideEnumPropertyEditorSupport extends PropertyEditorSupport {
    @Override
    public void setAsText(final String text) throws IllegalArgumentException {
        final SideEnum side = SideEnum.fromValue(text);
        if (isNull(side)) {
            throw new InvalidParametersException(format(INVALID_PARAMETER_EXCEPTION_MESSAGE, text));
        }
        setValue(side);
    }
}
