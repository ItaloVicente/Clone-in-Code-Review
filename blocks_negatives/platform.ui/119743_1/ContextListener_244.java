/*******************************************************************************
 * Copyright (c) 2000, 2019 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Lakshmi Shanmugam - initial API and implementation
 *******************************************************************************/
package org.eclipse.e4.ui.swt.internal.cocoa;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.css.swt.theme.ITheme;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
import org.eclipse.swt.internal.cocoa.OS;
import org.eclipse.swt.widgets.Display;
import org.osgi.service.event.EventHandler;

@SuppressWarnings("restriction")
public class CocoaDarkThemeProcessor {

	@Inject
	IEventBroker eventBroker;

	private EventHandler eventHandler;

	@PostConstruct
	public void intialize() {

		eventHandler = event -> {
			if (event == null) {
				return;
			}
			Display display = (Display) event.getProperty(IThemeEngine.Events.DEVICE);

			display.asyncExec(() -> OS.setTheme(isDark));
		};
		eventBroker.subscribe(IThemeEngine.Events.THEME_CHANGED, eventHandler);
	}

	@PreDestroy
	public void cleanUp() {
		eventBroker.unsubscribe(eventHandler);
	}

}
