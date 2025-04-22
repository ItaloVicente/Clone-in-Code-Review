
package org.eclipse.ui.examples.contributions.model;

import org.eclipse.ui.services.AbstractServiceFactory;
import org.eclipse.ui.services.IServiceLocator;

public class PersonServiceFactory extends AbstractServiceFactory {

	public Object create(Class serviceInterface, IServiceLocator parentLocator,
			IServiceLocator locator) {
		if (!IPersonService.class.equals(serviceInterface)) {
			return null;
		}
		Object parentService = parentLocator.getService(IPersonService.class);
		if (parentService == null) {
			return new PersonService(locator);
		}
		return new PersonServiceSlave(locator, (IPersonService) parentService);
	}

}
