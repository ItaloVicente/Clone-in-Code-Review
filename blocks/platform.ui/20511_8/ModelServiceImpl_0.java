
package org.eclipse.e4.ui.internal.workbench;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.services.internal.events.EventBroker;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.UIEvents.ElementContainer;
import org.eclipse.e4.ui.workbench.UIEvents.EventTags;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

@SuppressWarnings("restriction")
public class E4PerspectiveKeeper implements EventHandler {

	private final IEclipseContext context;

	private E4PerspectiveKeeper(IEclipseContext context) {
		this.context = context;
	}

	public void handleEvent(Event event) {
		if (!UIEvents.isSET(event))
			return;
		if (!(event.getProperty(EventTags.ELEMENT) instanceof MPerspectiveStack))
			return;

		Object perspectiveToDisp = event.getProperty(EventTags.NEW_VALUE);

		if (perspectiveToDisp instanceof MPerspective) {
			MPerspective perspective = (MPerspective) perspectiveToDisp;

			EModelService modelService = context.get(EModelService.class);
			if (modelService == null)
				return;

			MApplication application = context.get(MApplication.class);
			if (application == null)
				return;

			if (modelService.findSnippet(application, perspective.getElementId()) == null) {
				modelService.cloneElement(perspective, application, false);
			}
		}
	}

	public static E4PerspectiveKeeper registerNewInstance(IEclipseContext context) {
		return registerNewInstance(null, context);
	}

	public static E4PerspectiveKeeper registerNewInstance(IEventBroker eventBroker,
			IEclipseContext context) {

		if (context == null)
			throw new IllegalArgumentException("No IEclipseContext given!"); //$NON-NLS-1$

		if (eventBroker == null) {
			eventBroker = context.get(IEventBroker.class);
			if (eventBroker == null) {
				throw new IllegalArgumentException("No IEventBroker for registration given!"); //$NON-NLS-1$
			}
		}

		E4PerspectiveKeeper perspKeeper = new E4PerspectiveKeeper(context);
		eventBroker.subscribe(ElementContainer.TOPIC_SELECTEDELEMENT, perspKeeper);
		return perspKeeper;
	}
}
