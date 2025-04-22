package org.eclipse.e4.ui.macros.internal;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;

public class AcceptedCommandsServiceCreationFunction extends ContextFunction {

	@Override
	public Object compute(IEclipseContext context, String contextKey) {
		return ContextInjectionFactory.make(AcceptedCommandsImplementation.class, context);
	}

}
