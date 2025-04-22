package org.eclipse.e4.ui.internal;

import org.eclipse.e4.core.services.nls.ILocaleChangeService;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;

public class LocaleChangeServiceContextFunction extends ContextFunction {

	@Override
	public Object compute(IEclipseContext context, String contextKey) {
		ILocaleChangeService lcService = ContextInjectionFactory.make(
				LocaleChangeServiceImpl.class, context);

		MApplication application = context.get(MApplication.class);
		IEclipseContext ctx = application.getContext();
		ctx.set(ILocaleChangeService.class, lcService);
		return lcService;
	}
}
