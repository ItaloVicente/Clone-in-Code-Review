package org.eclipse.e4.ui.macros.internal.actions;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.ui.PlatformUI;


	private static IEclipseContext getActiveContext() {
		IEclipseContext parentContext = PlatformUI.getWorkbench().getService(IEclipseContext.class);
		return parentContext.getActiveLeaf();
	}

	public static <T> void execute(Class<T> clazz) {
		IEclipseContext context = getActiveContext();
		T impl = ContextInjectionFactory.make(clazz, context);
		ContextInjectionFactory.invoke(impl, Execute.class, context);
	}

}
