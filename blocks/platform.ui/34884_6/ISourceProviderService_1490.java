package org.eclipse.ui.services;

import org.eclipse.ui.ISourceProvider;

public interface IServiceWithSources extends IDisposable {

	public void addSourceProvider(ISourceProvider provider);

	public void removeSourceProvider(ISourceProvider provider);
}
