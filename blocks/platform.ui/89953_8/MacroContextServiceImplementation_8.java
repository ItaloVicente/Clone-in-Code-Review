package org.eclipse.e4.core.macros.internal;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.macros.EMacroContextService;

public class MacroContextServiceCreationFunction extends ContextFunction {

	private static EMacroContextService fService;

	@Override
	public Object compute(IEclipseContext context, String contextKey) {
		if (fService == null) {
			fService = ContextInjectionFactory.make(MacroContextServiceImplementation.class, context);
		}
		return fService;
	}
}
