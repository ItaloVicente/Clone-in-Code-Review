package org.eclipse.e4.core.macros.internal;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.macros.EMacroService;

public class MacroServiceCreationFunction extends ContextFunction {

	private static EMacroService fService;

	@Override
	public Object compute(IEclipseContext context, String contextKey) {
		if (fService == null) {
			fService = ContextInjectionFactory.make(MacroServiceImplementation.class, context);
		}
		return fService;
	}
}
