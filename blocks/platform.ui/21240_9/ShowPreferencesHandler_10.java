
package org.eclipse.e4.ui.progress;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.progress.internal.PreferenceStore;
import org.eclipse.e4.ui.progress.internal.ProgressManager;
import org.eclipse.e4.ui.progress.internal.Services;

public class ProgressViewAddon {

	@PostConstruct
	public void init(MApplication application, IEclipseContext context) {
		IEclipseContext appContext = application.getContext();
		appContext.set(PreferenceStore.class, ContextInjectionFactory.make(PreferenceStore.class, appContext));
		ContextInjectionFactory.make(Services.class, context);
		ProgressManager progressManager = ContextInjectionFactory.make(ProgressManager.class, context);
		appContext.set(ProgressManager.class, progressManager);
	}
}
