
package org.eclipse.ui.examples.contributions.editor;

import org.eclipse.core.commands.AbstractParameterValueConverter;
import org.eclipse.core.commands.ParameterValueConversionException;

public class IntegerTypeConverter extends AbstractParameterValueConverter {

	public Object convertToObject(String parameterValue)
			throws ParameterValueConversionException {
		try {
			return Integer.decode(parameterValue);
		} catch (NumberFormatException e) {
			throw new ParameterValueConversionException("Failed to decode", e); //$NON-NLS-1$
		}
	}

	public String convertToString(Object parameterValue)
			throws ParameterValueConversionException {
		if (!(parameterValue instanceof Integer)) {
			throw new ParameterValueConversionException("Failed to convert"); //$NON-NLS-1$
		}
		return parameterValue.toString();
	}

}
