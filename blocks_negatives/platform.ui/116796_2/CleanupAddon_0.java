/*******************************************************************************
 * Copyright (c) 2011, 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Lars Vogel <Lars.Vogel@vogella.com> - Bug 483842, 488537
 ******************************************************************************/

package org.eclipse.e4.ui.workbench.addons.cleanupaddon;

import javax.inject.Inject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.internal.workbench.swt.AbstractPartRenderer;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MArea;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.basic.MCompositePart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuElement;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.renderers.swt.SashLayout;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.event.Event;

public class CleanupAddon {
	@Inject
	IEventBroker eventBroker;

	@Inject
	EModelService modelService;

	@Inject
	MApplication app;

	@Inject
	@Optional
	private void subscribeTopicChildren(@UIEventTopic(UIEvents.ElementContainer.TOPIC_CHILDREN) Event event) {

		Display display = Display.getCurrent();
		Assert.isNotNull(display);

		Object changedObj = event.getProperty(UIEvents.EventTags.ELEMENT);

		if (!UIEvents.isREMOVE(event)) {
			return;
		}

		final MElementContainer<?> container = (MElementContainer<?>) changedObj;
		MUIElement containerParent = container.getParent();

		if (container instanceof MApplication || container instanceof MPerspectiveStack
				|| container instanceof MMenuElement || container instanceof MTrimBar || container instanceof MToolBar
				|| container instanceof MArea || container.getTags().contains(IPresentationEngine.NO_AUTO_COLLAPSE)) {
			return;
		}

		if (container instanceof MWindow && containerParent instanceof MApplication) {
			return;
		}



		Display.getCurrent().asyncExec(() -> {
			int tbrCount = modelService.toBeRenderedCount(container);

			boolean lastStack = isLastEditorStack(container);
			if (tbrCount == 0 && !lastStack) {
				container.setToBeRendered(false);
			}

			MElementContainer<?> lclContainer = container;
			if (lclContainer.getChildren().size() == 0) {
				MElementContainer<MUIElement> parent = container.getParent();
				if (parent != null && !lastStack) {
					container.setToBeRendered(false);
					parent.getChildren().remove(container);
				} else if (container instanceof MWindow) {
					MUIElement eParent = (MUIElement) ((EObject) container).eContainer();
					if (eParent instanceof MPerspective) {
						((MPerspective) eParent).getWindows().remove(container);
					} else if (eParent instanceof MWindow) {
						((MWindow) eParent).getWindows().remove(container);
					}
				}
			} else if (container.getChildren().size() == 1 && container instanceof MPartSashContainer) {
				MUIElement theChild = container.getChildren().get(0);
				MElementContainer<MUIElement> parentContainer = container.getParent();
				if (parentContainer != null) {
					int index = parentContainer.getChildren().indexOf(container);

					if (theChild instanceof MPartSashContainer) {
						if (container.getWidget() instanceof Composite) {
							Composite theComp = (Composite) container.getWidget();
							Object tmp = theChild.getWidget();
							theChild.setWidget(theComp);
							theComp.setLayout(new SashLayout(theComp, theChild));
							theComp.setData(AbstractPartRenderer.OWNING_ME, theChild);
							container.setWidget(tmp);
						}
					}

					theChild.setContainerData(container.getContainerData());
					container.getChildren().remove(theChild);
					parentContainer.getChildren().add(index, theChild);
					container.setToBeRendered(false);
					parentContainer.getChildren().remove(container);
				}
			}
		});
	}

	/**
	 * Returns true if and only if the given element should make itself visible
	 * when its first child becomes visible and make itself invisible whenever
	 * its last child becomes invisible. Defaults to false for unknown element
	 * types
	 */
	private static boolean shouldReactToChildVisibilityChanges(MUIElement theElement) {
		return (theElement instanceof MPartSashContainer || theElement instanceof MPartStack
				|| theElement instanceof MCompositePart)
				&& !theElement.getTags().contains(IPresentationEngine.HIDDEN_EXPLICITLY);
	}

	@Inject
	@Optional
	private void subscribeVisibilityChanged(
			@UIEventTopic(UIEvents.UIElement.TOPIC_VISIBLE) Event event) {
		MUIElement changedObj = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);
		if (changedObj instanceof MTrimBar || ((Object) changedObj.getParent()) instanceof MToolBar) {
			return;
		}

		if (changedObj.getWidget() instanceof Shell) {
			((Shell) changedObj.getWidget()).setVisible(changedObj.isVisible());
		} else if (changedObj.getWidget() instanceof Rectangle) {
			MElementContainer<MUIElement> parent = changedObj.getParent();
			if (!shouldReactToChildVisibilityChanges(parent)) {
				return;
			}

			if (changedObj.isVisible()) {
				if (!parent.isVisible()) {
					parent.setVisible(true);
				}
			} else {
				boolean makeInvisible = true;
				for (MUIElement kid : parent.getChildren()) {
					if (kid.isToBeRendered() && kid.isVisible()) {
						makeInvisible = false;
						break;
					}
				}
				if (makeInvisible) {
					parent.setVisible(false);
				}
			}
		} else if (changedObj.getWidget() instanceof Control) {
			Control ctrl = (Control) changedObj.getWidget();
			MElementContainer<MUIElement> parent = changedObj.getParent();
			if (parent == null || ((Object) parent) instanceof MToolBar) {
				return;
			}

			if (changedObj.isVisible()) {
				if (parent.getRenderer() != null) {
					Object myParent = ((AbstractPartRenderer) parent.getRenderer())
							.getUIContainer(changedObj);
					if (myParent instanceof Composite) {
						Composite parentComp = (Composite) myParent;
						ctrl.setParent(parentComp);

						Control prevControl = null;
						for (MUIElement childME : parent.getChildren()) {
							if (childME == changedObj) {
								break;
							}
							if (childME.getWidget() instanceof Control && childME.isVisible()) {
								prevControl = (Control) childME.getWidget();
							}
						}
						if (prevControl != null) {
							ctrl.moveBelow(prevControl);
						} else {
							ctrl.moveAbove(null);
						}
						ctrl.requestLayout();
					}

					if (!shouldReactToChildVisibilityChanges(parent)) {
						return;
					}

					if (!parent.isVisible()) {
						parent.setVisible(true);
					}
				}
			} else {
				Shell limbo = (Shell) app.getContext().get("limbo");

				Composite curParent = ctrl.getParent();
				ctrl.setParent(limbo);
				curParent.requestLayout();

				if ((Object) parent instanceof MWindow) {
					return;
				}

				boolean makeParentInvisible = true;
				for (MUIElement kid : parent.getChildren()) {
					if (kid.isToBeRendered() && kid.isVisible()) {
						makeParentInvisible = false;
						break;
					}
				}

				if (isLastEditorStack(parent) && makeParentInvisible) {
					return;
				}

				if (!shouldReactToChildVisibilityChanges(parent)) {
					return;
				}

				if (makeParentInvisible) {
					parent.setVisible(false);
				}
			}
		}
	}

	@Inject
	@Optional
	private void subscribeRenderingChanged(
			@UIEventTopic(UIEvents.UIElement.TOPIC_TOBERENDERED) Event event) {
			MUIElement changedObj = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);
			MElementContainer<MUIElement> container = null;
			if (changedObj.getCurSharedRef() != null) {
				container = changedObj.getCurSharedRef().getParent();
			} else {
				container = changedObj.getParent();
			}

			if (container == null) {
				return;
			}

			MUIElement containerElement = container;
			if (containerElement instanceof MWindow && containerElement.getParent() != null) {
				return;
			}

			if (isLastEditorStack(containerElement) || containerElement instanceof MPerspective
					|| containerElement instanceof MPerspectiveStack) {
				return;
			}

			Boolean toBeRendered = (Boolean) event.getProperty(UIEvents.EventTags.NEW_VALUE);
			if (toBeRendered) {
				if (!container.isToBeRendered()) {
					container.setToBeRendered(true);
				}
				if (!container.isVisible()
						&& !container.getTags().contains(IPresentationEngine.MINIMIZED)) {
					container.setVisible(true);
				}
			} else {
				if (container.getTags().contains(IPresentationEngine.NO_AUTO_COLLAPSE)) {
					return;
				}

				int visCount = modelService.countRenderableChildren(container);

				final MElementContainer<MUIElement> theContainer = container;
				if (visCount == 0) {
				Display.getCurrent().asyncExec(() -> {
					int visCount1 = modelService.countRenderableChildren(theContainer);
					if (!isLastEditorStack(theContainer) && visCount1 == 0) {
						theContainer.setToBeRendered(false);
						}
					});
				} else {
					boolean makeInvisible = true;

					for (MUIElement kid : container.getChildren()) {
						if (!kid.isToBeRendered()) {
							continue;
						}
						if (kid.isVisible()) {
							makeInvisible = false;
							break;
						}
					}

					if (makeInvisible) {
						container.setVisible(false);
					}
				}
			}
	}

	boolean isLastEditorStack(MUIElement element) {
		return modelService.isLastEditorStack(element);
	}
}
