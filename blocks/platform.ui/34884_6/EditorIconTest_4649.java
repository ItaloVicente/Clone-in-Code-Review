package org.eclipse.ui.tests.api;

import org.eclipse.ui.services.AbstractServiceFactory;
import org.eclipse.ui.services.IServiceLocator;

public class DummyServiceFactory extends AbstractServiceFactory {
	
	@Override
	public Object create(Class serviceInterface, IServiceLocator parentLocator,
			IServiceLocator locator) {
		if(serviceInterface.equals(DummyService.class))
			return new DummyService();
		return null;
	}

}
