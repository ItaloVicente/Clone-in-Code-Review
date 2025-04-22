package org.eclipse.e4.demo.contacts.model.internal;

import javax.inject.Inject;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.translation.TranslationService;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.lifecycle.ProcessRemovals;

public class ModelLifeCycleExtension {
	
	@Inject
	public ModelLifeCycleExtension() {
	}

	@ProcessRemovals
	public void overrideTranslation(MApplication application) {
		IEclipseContext appContext = application.getContext();
		BinaryTranslatorProvider translationService = ContextInjectionFactory.make(BinaryTranslatorProvider.class, appContext);
		appContext.set(TranslationService.class, translationService);
	}

}
