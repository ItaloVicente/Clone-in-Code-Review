package org.eclipse.ui.internal.commands;

import org.eclipse.core.commands.AbstractParameterValueConverter;
import org.eclipse.core.commands.ParameterValueConversionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

public final class ParameterValueConverterProxy extends
		AbstractParameterValueConverter {

	private final IConfigurationElement converterConfigurationElement;

	private AbstractParameterValueConverter parameterValueConverter;

	public ParameterValueConverterProxy(
			final IConfigurationElement converterConfigurationElement) {
		if (converterConfigurationElement == null) {
			throw new NullPointerException(
					"converterConfigurationElement must not be null"); //$NON-NLS-1$
		}

		this.converterConfigurationElement = converterConfigurationElement;
	}

	@Override
	public final Object convertToObject(final String parameterValue)
			throws ParameterValueConversionException {
		return getConverter().convertToObject(parameterValue);
	}

	@Override
	public final String convertToString(final Object parameterValue)
			throws ParameterValueConversionException {
		return getConverter().convertToString(parameterValue);
	}

	private AbstractParameterValueConverter getConverter()
			throws ParameterValueConversionException {
		if (parameterValueConverter == null) {
			try {
				parameterValueConverter = (AbstractParameterValueConverter) converterConfigurationElement
						.createExecutableExtension(IWorkbenchRegistryConstants.ATT_CONVERTER);
			} catch (final CoreException e) {
				throw new ParameterValueConversionException(
						"Problem creating parameter value converter", e); //$NON-NLS-1$
			} catch (final ClassCastException e) {
				throw new ParameterValueConversionException(
						"Parameter value converter was not a subclass of AbstractParameterValueConverter", e); //$NON-NLS-1$
			}
		}
		return parameterValueConverter;
	}
}
