
package org.eclipse.ui.services;

import org.eclipse.ui.ISourceProvider;

public interface ISourceProviderService {

	public ISourceProvider getSourceProvider(final String sourceName);

	public ISourceProvider[] getSourceProviders();

}
