/*******************************************************************************
 * Copyright (c) 2011, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.e4.ui.tests.workbench;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class SampleToolControl {


	boolean shellDisposed = false;
	boolean shellEagerlyDestroyed = false;

	@PostConstruct
	void construct(MWindow window) {
		Shell shell = (Shell) window.getWidget();
		shell.addListener(SWT.Dispose, new Listener() {
			@Override
			public void handleEvent(Event event) {
				shellDisposed = true;
			}
		});
	}

	@PreDestroy
	void destroy() {
		if (shellDisposed) {
			shellEagerlyDestroyed = true;
		}
	}

}
