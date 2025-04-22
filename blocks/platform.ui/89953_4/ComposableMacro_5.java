package org.eclipse.e4.core.macros;

import javax.annotation.PostConstruct;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.macros.internal.MacroContextServiceCreationFunction;

public final class MacroServiceAddon {

	@PostConstruct
	public void init(IEclipseContext context) {
		context.set(EMacroContext.class.getName(), new MacroContextServiceCreationFunction());
	}

}
