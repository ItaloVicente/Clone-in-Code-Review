
package org.eclipse.ui;

import java.util.Map;

public interface ISourceProvider {

	public void addSourceProviderListener(ISourceProviderListener listener);

	public void dispose();

	public Map getCurrentState();

	public String[] getProvidedSourceNames();

	public void removeSourceProviderListener(ISourceProviderListener listener);
}
