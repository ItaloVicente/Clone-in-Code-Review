/*******************************************************************************
 * Copyright (c) 2017 Patrik Suzzi. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 *
 * Contributors:
 * 	Patrik Suzzi <psuzzi@gmail.com> - initial API and implementation;
 ******************************************************************************/

package org.eclipse.ui.internal.handlers;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchWindow;
import org.eclipse.ui.internal.services.IWorkbenchLocationService;
import org.eclipse.ui.menus.UIElement;
import org.eclipse.ui.services.IServiceScopes;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

/**
 * Toggle the visibility of the status bar. Implementation of the
 * {@code org.eclipse.ui.window.togglestatusbar} command.
 *
 */
public class ToggleStatusBarHandler extends AbstractHandler implements IElementUpdater {



	private HashMap<IWorkbenchWindow, EventHandler> eventHandlers = new HashMap<>();
	private HashMap<IWorkbenchWindow, IEventBroker> eventBrokers = new HashMap<>();

	@Override
	public Object execute(ExecutionEvent event) {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		if (!(window instanceof WorkbenchWindow))
			return null;
		if (!eventHandlers.containsKey(window)) {
			initializeEventHandler(window);
		}
		MUIElement trimStatus = getTrimStatus((WorkbenchWindow) window);
		if (trimStatus != null) {
			trimStatus.setVisible(!trimStatus.isVisible());
		}
		return null;
	}

	/**
	 * @param window
	 */
	private void initializeEventHandler(IWorkbenchWindow window) {
		final IEventBroker eventBroker = window.getService(IEventBroker.class);
		eventBrokers.put(window, eventBroker);
		EventHandler eventHandler = new EventHandler() {
			@Override
			public void handleEvent(Event event) {
				Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
				if (element != null && element == getTrimStatus((WorkbenchWindow) window)) {
					ICommandService commandService = window.getService(ICommandService.class);
					Map<String, WorkbenchWindow> filter = new HashMap<>();
					filter.put(IServiceScopes.WINDOW_SCOPE, (WorkbenchWindow) window);
					commandService.refreshElements(COMMAND_ID_TOGGLE_STATUSBAR, filter);
				}
			}
		};
		eventHandlers.put(window, eventHandler);
		eventBroker.subscribe(UIEvents.UIElement.TOPIC_VISIBLE, eventHandler);
	}

	@Override
	public void dispose() {
		for (IWorkbenchWindow w : eventHandlers.keySet()) {
			IEventBroker eventBroker = eventBrokers.get(w);
			EventHandler eventHandler = eventHandlers.get(w);
			if (eventBroker != null && eventHandler != null) {
				eventBroker.unsubscribe(eventHandler);
			}
		}
		super.dispose();
	}

	/**
	 * Updates the visibilty status of the element.
	 */
	@Override
	public void updateElement(UIElement element, Map parameters) {
		IWorkbenchLocationService wls = element
				.getServiceLocator()
				.getService(IWorkbenchLocationService.class);
		IWorkbenchWindow window = wls.getWorkbenchWindow();
		if (!(window instanceof WorkbenchWindow))
			return;
		MUIElement trimStatus = getTrimStatus((WorkbenchWindow) window);
		if(trimStatus != null) {
			element.setText(trimStatus.isVisible() ? WorkbenchMessages.ToggleStatusBarVisibilityAction_hide_text
					: WorkbenchMessages.ToggleStatusBarVisibilityAction_show_text);
		}
	}

	/* Get the MUIElement representing the status bar for the given window */
	private static MUIElement getTrimStatus(WorkbenchWindow window) {
		EModelService modelService = window.getService(EModelService.class);
		MUIElement searchRoot = window.getModel();
		return modelService.find(BOTTOM_TRIM_ID, searchRoot);
	}

}
