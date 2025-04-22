
package org.eclipse.e4.ui.internal.workbench.addons;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.UIEvents.ElementContainer;
import org.eclipse.e4.ui.workbench.UIEvents.EventTags;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPerspectiveRestoreService;
import org.osgi.service.event.Event;

@SuppressWarnings("restriction")
public class E4PerspectiveResetAddon {

	@PostConstruct
	public void init(IEclipseContext eclipseContext) {
		eclipseContext.set(EPerspectiveRestoreService.class,
				new SnippetBasedPerspectiveRestoreService(eclipseContext));
	}

	@Inject
	public void keepState(
			@Optional @EventTopic(ElementContainer.TOPIC_SELECTEDELEMENT) Event event,
			MApplication application, EModelService modelService) {
		if (event == null)
			return;

		if (!UIEvents.isSET(event))
			return;

		if (!(event.getProperty(EventTags.ELEMENT) instanceof MPerspectiveStack))
			return;

		Object perspectiveToDisp = event.getProperty(EventTags.NEW_VALUE);

		if (perspectiveToDisp instanceof MPerspective) {
			MPerspective perspective = (MPerspective) perspectiveToDisp;

			if (modelService.findSnippet(application, perspective.getElementId()) == null) {
				modelService.cloneElement(perspective, application);
			}
		}
	}

	private static final class SnippetBasedPerspectiveRestoreService implements
			EPerspectiveRestoreService {

		private final IEclipseContext appContext;

		public SnippetBasedPerspectiveRestoreService(IEclipseContext appContext) {
			if (appContext == null) {
				throw new IllegalArgumentException("No IEclipseContext given!"); //$NON-NLS-1$
			}

			this.appContext = appContext;
		}

		public MPerspective reloadPerspective(String perspectiveID, MWindow window) {
			if (window == null || perspectiveID == null)
				return null;


			EModelService modelService = appContext.get(EModelService.class);
			if (modelService == null)
				return null;

			MApplication application = appContext.get(MApplication.class);

			MUIElement storedPerspState = modelService.cloneSnippet(application, perspectiveID,
					window);
			if (storedPerspState instanceof MPerspective) {
				return (MPerspective) storedPerspState;
			}

			return null;
		}

	}
}
