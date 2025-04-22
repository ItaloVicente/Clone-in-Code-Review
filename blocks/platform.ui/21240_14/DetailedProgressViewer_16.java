
package org.eclipse.e4.ui.progress.internal;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

@Creatable
@Singleton
public class ContentProviderFactory {
	
	@Inject
	Services services;
	
	@PostConstruct
	void init() {
		services.registerService(ContentProviderFactory.class, this);
	}

	public ProgressViewerContentProvider getProgressViewerContentProvider(
	        AbstractProgressViewer structured, boolean debug,
	        boolean showFinished) {

		return new ProgressViewerContentProvider(structured,
				services.getService(FinishedJobs.class),
		        services.getService(ProgressViewUpdater.class),
		        services.getService(ProgressManager.class), debug, showFinished);
	}
	
}
