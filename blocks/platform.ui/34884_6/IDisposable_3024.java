
package org.eclipse.ui.services;

public abstract class AbstractServiceFactory {

	public abstract Object create(Class serviceInterface,
			IServiceLocator parentLocator, IServiceLocator locator);
}
