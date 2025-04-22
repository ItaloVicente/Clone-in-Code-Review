
package org.eclipse.ui.commands;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.core.commands.IParameterValues;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;

public final class ExtensionParameterValues implements IParameterValues,
		IExecutableExtension {

	public static final String DELIMITER = ","; //$NON-NLS-1$

	private Map parameterValues = null;

	@Override
	public Map getParameterValues() {
		return parameterValues;
	}

	@Override
	public final void setInitializationData(final IConfigurationElement config,
			final String propertyName, final Object data) {
		if (data == null) {
			parameterValues = Collections.EMPTY_MAP;

		} else if (data instanceof String) {
			parameterValues = new HashMap();
			final StringTokenizer tokenizer = new StringTokenizer(
					(String) data, DELIMITER);
			while (tokenizer.hasMoreTokens()) {
				final String name = tokenizer.nextToken();
				if (tokenizer.hasMoreTokens()) {
					final String value = tokenizer.nextToken();
					parameterValues.put(name, value);
				}
			}
			parameterValues = Collections.unmodifiableMap(parameterValues);

		} else if (data instanceof Hashtable) {
			parameterValues = Collections.unmodifiableMap((Hashtable) data);

		}

	}
}
