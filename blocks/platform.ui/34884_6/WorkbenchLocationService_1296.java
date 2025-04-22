
package org.eclipse.ui.internal.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.ISourceProvider;
import org.eclipse.ui.services.IDisposable;
import org.eclipse.ui.services.IServiceLocator;
import org.eclipse.ui.services.ISourceProviderService;

public final class SourceProviderService implements ISourceProviderService,
		IDisposable {

	private final Map sourceProvidersByName = new HashMap();

	private final Set sourceProviders = new HashSet();

	private IServiceLocator locator;
	
	public SourceProviderService(final IServiceLocator locator) {
		this.locator = locator;
	}

	@Override
	public final void dispose() {
		final Iterator sourceProviderItr = sourceProviders.iterator();
		while (sourceProviderItr.hasNext()) {
			final ISourceProvider sourceProvider = (ISourceProvider) sourceProviderItr
					.next();
			sourceProvider.dispose();
		}
		sourceProviders.clear();
		sourceProvidersByName.clear();
	}

	@Override
	public final ISourceProvider getSourceProvider(final String sourceName) {
		return (ISourceProvider) sourceProvidersByName.get(sourceName);
	}

	@Override
	public final ISourceProvider[] getSourceProviders() {
		return (ISourceProvider[]) sourceProviders
				.toArray(new ISourceProvider[sourceProviders.size()]);
	}

	public final void registerProvider(final ISourceProvider sourceProvider) {
		if (sourceProvider == null) {
			throw new NullPointerException("The source provider cannot be null"); //$NON-NLS-1$
		}

		final String[] sourceNames = sourceProvider.getProvidedSourceNames();
		for (int i = 0; i < sourceNames.length; i++) {
			final String sourceName = sourceNames[i];
			sourceProvidersByName.put(sourceName, sourceProvider);
		}
		sourceProviders.add(sourceProvider);
	}

	public final void unregisterProvider(ISourceProvider sourceProvider) {
		if (sourceProvider == null) {
			throw new NullPointerException("The source provider cannot be null"); //$NON-NLS-1$
		}

		final String[] sourceNames = sourceProvider.getProvidedSourceNames();
		for (int i = 0; i < sourceNames.length; i++) {
			sourceProvidersByName.remove(sourceNames[i]);
		}
		sourceProviders.remove(sourceProvider);
	}
		
	public final void readRegistry() {
		AbstractSourceProvider[] sp = WorkbenchServiceRegistry.getRegistry().getSourceProviders();
		for (int i = 0; i < sp.length; i++) {
			sp[i].initialize(locator);
			registerProvider(sp[i]);
		}
	}
}
