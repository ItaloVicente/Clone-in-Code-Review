package org.eclipse.ui.tests.commands;

import org.eclipse.core.commands.AbstractParameterValueConverter;
import org.eclipse.core.commands.ParameterValueConversionException;

public class IntegerConverter extends AbstractParameterValueConverter {

	@Override
	public Object convertToObject(String parameterValue)
			throws ParameterValueConversionException {
		try {
			int val = Integer.parseInt(parameterValue);
			return new Integer(val);
		} catch (NumberFormatException ex) {
			throw new ParameterValueConversionException(
					"Error parsing value: " + parameterValue, ex);
		}
	}
	
	@Override
	public String convertToString(Object parameterValue)
			throws ParameterValueConversionException {
		if (!(parameterValue instanceof Integer)) {
			throw new ParameterValueConversionException("Invalid object type: "
					+ parameterValue);
		}
		Integer val = (Integer) parameterValue;
		return val.toString();
	}
}
