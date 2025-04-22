/*******************************************************************************
 * Copyright (c) 2017, 2020 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jface.tests.window;

import org.eclipse.core.runtime.Status;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.widgets.Shell;

import junit.framework.TestCase;

public class ApplicationWindowTest extends TestCase {

	private ApplicationWindow window;

	@Override
	protected void tearDown() throws Exception {
		if (window != null) {
			window.close();
			window = null;
		}
		super.tearDown();
	}

	private void testBug334093(boolean fork, boolean cancelable) throws Exception {
		window = new ApplicationWindow(null) {
			@Override
			public void create() {
				addStatusLine();
				super.create();
			}

			@Override
			protected void createTrimWidgets(Shell shell) {
			}
		};
		window.create();
		window.run(fork, cancelable, monitor -> {
			monitor.beginTask("beginTask", 10);
			monitor.setTaskName("setTaskName");
			monitor.subTask("subTask");

			monitor.setBlocked(Status.CANCEL_STATUS);
			monitor.clearBlocked();

			monitor.worked(1);
			monitor.setCanceled(true);
			monitor.isCanceled();
			monitor.setCanceled(false);
			monitor.done();
		});
	}

	public void testBug334093() throws Exception {
		boolean[] options = new boolean[] { true, false };
		for (boolean forkOption : options) {
			for (boolean cancelableOpton : options) {
				testBug334093(forkOption, cancelableOpton);
			}
		}
	}
}
