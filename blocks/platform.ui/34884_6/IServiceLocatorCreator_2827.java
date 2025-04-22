
package org.eclipse.ui.internal.services;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.ui.services.AbstractServiceFactory;
import org.eclipse.ui.services.IDisposable;
import org.eclipse.ui.services.IServiceLocator;

public interface IServiceLocatorCreator {
	public IServiceLocator createServiceLocator(IServiceLocator parent,
			AbstractServiceFactory factory, IDisposable owner);

	public IServiceLocator createServiceLocator(IServiceLocator parent,
			AbstractServiceFactory factory, IDisposable owner, IEclipseContext context);

}
