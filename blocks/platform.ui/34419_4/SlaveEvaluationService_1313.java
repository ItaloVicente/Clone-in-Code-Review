
package org.eclipse.ui.internal.services;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.ui.services.AbstractServiceFactory;
import org.eclipse.ui.services.IDisposable;
import org.eclipse.ui.services.IServiceLocator;

public class ServiceLocatorCreator implements IServiceLocatorCreator {

	@Override
	public IServiceLocator createServiceLocator(IServiceLocator parent,
			AbstractServiceFactory factory, IDisposable owner) {
		ServiceLocator serviceLocator = new ServiceLocator(parent, factory, owner);
		if (parent != null) {
			IEclipseContext ctx = parent.getService(IEclipseContext.class);
			if (ctx != null) {
				serviceLocator.setContext(ctx.createChild());
			}
		}
		return serviceLocator;
	}

	@Override
	public IServiceLocator createServiceLocator(IServiceLocator parent,
			AbstractServiceFactory factory, IDisposable owner, IEclipseContext context) {
		ServiceLocator serviceLocator = new ServiceLocator(parent, factory, owner);
		serviceLocator.setContext(context);
		return serviceLocator;
	}
}
