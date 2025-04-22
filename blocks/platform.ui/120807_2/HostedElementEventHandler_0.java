
package org.eclipse.e4.ui.internal.workbench;

import javax.inject.Inject;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.UIEvents.EventTags;
import org.eclipse.emf.ecore.EObject;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class HostedElementEventHandler implements EventHandler {

	@Inject
	@Optional
	UISynchronize uiSync;

	@Override
	public void handleEvent(Event event) {
		if (uiSync != null) {
			uiSync.syncExec(() -> {
				final MUIElement changedElement = (MUIElement) event.getProperty(EventTags.ELEMENT);
				if (!changedElement.getTags().contains(ModelServiceImpl.HOSTED_ELEMENT)) {
					return;
				}

				if (changedElement.getWidget() != null) {
					return;
				}

				EObject eObj = (EObject) changedElement;
				if (!(eObj.eContainer() instanceof MWindow)) {
					return;
				}

				MWindow hostingWindow = (MWindow) eObj.eContainer();
				hostingWindow.getSharedElements().remove(changedElement);
				changedElement.getTags().remove(ModelServiceImpl.HOSTED_ELEMENT);
			});
		}
	}
}
