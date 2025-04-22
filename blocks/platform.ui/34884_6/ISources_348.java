
package org.eclipse.ui;

import java.util.Map;

public interface ISourceProviderListener {

	public void sourceChanged(final int sourcePriority,
			final Map sourceValuesByName);

	public void sourceChanged(final int sourcePriority,
			final String sourceName, final Object sourceValue);
}
