
package org.eclipse.e4.ui.internal.workbench;

import java.util.List;
import javax.inject.Inject;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.UIEvents.EventTags;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.osgi.service.event.Event;

@SuppressWarnings("restriction")
public class PartOnTopManager {

	@Inject
	private EModelService modelService;

	@Inject
	@Optional
	public void subscribeTopicWidget(@UIEventTopic(UIEvents.UIElement.TOPIC_WIDGET) Event event) {
		Object element = event.getProperty(EventTags.ELEMENT);
		Object newValue = event.getProperty(EventTags.NEW_VALUE);

		if (element instanceof MPart && newValue != null) {
			handlePartSelection(null, element);
		}
	}

	@Inject
	@Optional
	public void subscribeTopicSelectedElement(
			@EventTopic(UIEvents.ElementContainer.TOPIC_SELECTEDELEMENT) Event event) {

		Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
		Object oldValue = event.getProperty(UIEvents.EventTags.OLD_VALUE);
		Object newValue = event.getProperty(UIEvents.EventTags.NEW_VALUE);

		if (element instanceof MPerspectiveStack) {
			handlePerspectiveSwitch(oldValue, newValue);
		} else if (element instanceof MPartStack) {
			handlePartSelection(oldValue, newValue);
		}

	}

	private void handlePerspectiveSwitch(Object oldValue, Object newValue) {
		handleNewAndOldSelectedElementsOfElementContainer(oldValue, newValue);
	}

	private void handlePartSelection(Object oldValue, Object newValue) {
		Object oldElement = oldValue;

		if (oldElement instanceof MPlaceholder) {
			oldElement = ((MPlaceholder) oldElement).getRef();
		}

		if (oldElement instanceof MPart) {
			MPart contextElement = (MPart) oldElement;
			if (contextElement.getContext() != null) {
				contextElement.getContext().set(IWorkbench.ON_TOP, Boolean.FALSE);
			}
		}

		Object newElement = newValue;

		if (newElement instanceof MPlaceholder) {
			newElement = ((MPlaceholder) newElement).getRef();
		}

		if (newElement instanceof MPart) {
			MPart contextElement = (MPart) newElement;
			if (contextElement.getContext() != null) {
				contextElement.getContext().set(IWorkbench.ON_TOP, Boolean.TRUE);
			}
		}

		handleNewAndOldSelectedElementsOfElementContainer(oldElement, newElement);
	}

	private void handleNewAndOldSelectedElementsOfElementContainer(Object oldValue, Object newValue) {
		if (oldValue instanceof MElementContainer && newValue instanceof MElementContainer) {
			if (oldValue instanceof MElementContainer) {
				List<MPart> newParts = modelService.findElements((MElementContainer<?>) newValue, null, MPart.class,
						null);
				List<MPart> oldParts = modelService.findElements((MElementContainer<?>) oldValue, null, MPart.class,
						null);
				oldParts.forEach(part -> {
					if (part.getContext() != null) {
						if (checkPartOnTop(newParts, part)) {
							part.getContext().set(IWorkbench.ON_TOP, Boolean.FALSE);
						}
					}
				});
			}
		} else if (newValue instanceof MElementContainer) {
			MElementContainer<?> newElementContainer = (MElementContainer<?>) newValue;
			List<MPart> parts = modelService.findElements(newElementContainer, null, MPart.class, null);
			handleNewSelectedElementsOfElementContainer(parts);
		}
	}

	private boolean checkPartOnTop(List<MPart> newParts, MPart part) {
		if (part.getCurSharedRef() != null) {
			if (newParts.contains(part)) {
				java.util.Optional<MPartStack> partStackContainer = getPartStackContainer(part.getCurSharedRef());
				if (partStackContainer.isPresent()) {
					return part.getCurSharedRef().equals(partStackContainer.get().getSelectedElement());
				}
			}
		}
		return false;
	}

	private void handleNewSelectedElementsOfElementContainer(List<MPart> parts) {
		parts.stream().filter(part -> {
			if (part.getContext() == null) {
				return false;
			}
			MUIElement partRef = part;
			if (part.getCurSharedRef() != null) {
				partRef = part.getCurSharedRef();
			}
			java.util.Optional<MPartStack> partStackContainer = getPartStackContainer(partRef);
			if (partStackContainer.isPresent()) {
				return partRef.equals(partStackContainer.get().getSelectedElement());
			}

			return true;
		}).forEach(part -> {
			part.getContext().set(IWorkbench.ON_TOP, Boolean.TRUE);
		});
	}

	private java.util.Optional<MPartStack> getPartStackContainer(MUIElement uiElement) {
		MUIElement parent = modelService.getContainer(uiElement);
		if (parent instanceof MPartStack) {
			return java.util.Optional.of((MPartStack) parent);
		} else if (parent instanceof MWindow) {
			return java.util.Optional.empty();
		}

		return getPartStackContainer(parent);
	}
}
