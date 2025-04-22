
package org.eclipse.e4.ui.internal.workbench.addons;

import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplicationElement;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.UIEvents.EventTags;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.osgi.service.event.Event;

@SuppressWarnings("restriction")
public class ElementOnTopAddon {

	@Inject
	private EModelService modelService;

	@Inject
	@Optional
	public void subscribeTopicWidget(@UIEventTopic(UIEvents.UIElement.TOPIC_WIDGET) Event event) {
		Object element = event.getProperty(EventTags.ELEMENT);
		Object newValue = event.getProperty(EventTags.NEW_VALUE);

		if (element instanceof MStackElement) {
			MStackElement stackElement = (MStackElement) element;
			if (newValue != null && checkOnTop(stackElement)) {
				stackElement.getTags().add(IPresentationEngine.ON_TOP);
			} else {
				stackElement.getTags().remove(IPresentationEngine.ON_TOP);
			}
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
			handlePartStacksSelection(oldValue, newValue);
		}

	}

	private void handlePerspectiveSwitch(Object oldValue, Object newValue) {
		handleOldPerspective(oldValue);

		handleNewPerspective(newValue);
	}

	private void handleOldPerspective(Object oldValue) {
		if (oldValue instanceof MPerspective) {
			MPerspective oldPerspective = (MPerspective) oldValue;
			List<MStackElement> elements = modelService.findElements(oldPerspective, null, MStackElement.class,
					Collections.singletonList(IPresentationEngine.ON_TOP), EModelService.IN_ACTIVE_PERSPECTIVE);
			elements.forEach(stackElement -> stackElement.getTags().remove(IPresentationEngine.ON_TOP));
		}
	}

	private void handleNewPerspective(Object newValue) {
		if (newValue instanceof MPerspective) {
			MPerspective newPerspective = (MPerspective) newValue;

			List<MPartStack> partStacks = modelService.findElements(newPerspective, null, MPartStack.class, null,
					EModelService.IN_ACTIVE_PERSPECTIVE);
			partStacks.stream().filter(partStack -> partStack.getSelectedElement() != null && partStack.isVisible())
					.forEach(partStack -> partStack.getSelectedElement().getTags().add(IPresentationEngine.ON_TOP));

			List<MStackElement> stackElements = modelService.findElements(newPerspective, null, MStackElement.class,
					null, EModelService.IN_ACTIVE_PERSPECTIVE);
			stackElements.stream().filter(this::hasNoPartStackContainer)
					.forEach(stackElement -> stackElement.getTags().add(IPresentationEngine.ON_TOP));
		}
	}

	private boolean checkOnTop(MUIElement uiElement) {
		java.util.Optional<MPartStack> partStackContainer = getPartStackContainer(uiElement);
		if (partStackContainer.isPresent()) {
			if (!uiElement.equals(partStackContainer.get().getSelectedElement())) {
				return false;
			}
		}

		return true;
	}

	private boolean hasNoPartStackContainer(MUIElement uiElement) {
		MWindow topLevelWindow = modelService.getTopLevelWindowFor(uiElement);
		if (topLevelWindow.getSharedElements().contains(uiElement)) {
			return false;
		}

		return !getPartStackContainer(uiElement).isPresent();
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

	private void handlePartStacksSelection(Object oldValue, Object newValue) {
		if (oldValue instanceof MApplicationElement) {
			MApplicationElement stackElement = (MApplicationElement) oldValue;
			stackElement.getTags().remove(IPresentationEngine.ON_TOP);
		}

		if (newValue instanceof MApplicationElement) {
			MApplicationElement stackElement = (MApplicationElement) newValue;
			stackElement.getTags().add(IPresentationEngine.ON_TOP);
		}
	}

}
