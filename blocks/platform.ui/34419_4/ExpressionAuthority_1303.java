
package org.eclipse.ui.internal.services;

import org.eclipse.ui.services.AbstractServiceFactory;
import org.eclipse.ui.services.IEvaluationService;
import org.eclipse.ui.services.IServiceLocator;

public class EvaluationServiceFactory extends AbstractServiceFactory {

	@Override
	public Object create(Class serviceInterface, IServiceLocator parentLocator,
			IServiceLocator locator) {
		if (!IEvaluationService.class.equals(serviceInterface)) {
			return null;
		}
		Object parent = parentLocator.getService(serviceInterface);
		if (parent == null) {
			return null;
		}
		return new SlaveEvaluationService((IEvaluationService)parent);
	}

}
