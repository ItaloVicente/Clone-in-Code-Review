/*******************************************************************************
 * Copyright (c) 2005, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.ui.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.NodeChangeEvent;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.internal.InternalPolicy;
import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.jface.util.Policy;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.util.StatusHandler;
import org.eclipse.ui.statushandlers.StatusAdapter;
import org.eclipse.ui.statushandlers.StatusManager;

/**
 * Utility class for setting up JFace for use by Eclipse.
 *
 * @since 3.1
 */
final class JFaceUtil {

	private JFaceUtil() {
	}

	/**
	 * Initializes JFace for use by Eclipse.
	 */
	public static void initializeJFace() {
		SafeRunnable.setRunner(code -> SafeRunner.run(code));

		Policy.setLog(status -> {
			if (status.getSeverity() == IStatus.WARNING
					|| status.getSeverity() == IStatus.ERROR) {
				StatusManager.getManager().handle(status);
			} else {
				WorkbenchPlugin.log(status);
			}
		});

		Policy.setStatusHandler(new StatusHandler() {
			@Override
			public void show(IStatus status, String title) {
				StatusAdapter statusAdapter = new StatusAdapter(status);
				statusAdapter.setProperty(StatusAdapter.TITLE_PROPERTY, title);
				StatusManager.getManager().handle(statusAdapter, StatusManager.SHOW);
			}
		});

		}
	}

	/**
	 * Adds a preference listener so that the JFace preference store is initialized
	 * as soon as the workbench preference store becomes available.
	 */
	public static void initializeJFacePreferences() {
		IEclipsePreferences rootNode = (IEclipsePreferences) Platform.getPreferencesService().getRootNode().node(InstanceScope.SCOPE);
		final String workbenchName = WorkbenchPlugin.getDefault().getBundle().getSymbolicName();

		rootNode.addNodeChangeListener(new IEclipsePreferences.INodeChangeListener() {
			@Override
			public void added(NodeChangeEvent event) {
				if (!event.getChild().name().equals(workbenchName)) {
					return;
				}
				((IEclipsePreferences) event.getChild()).addPreferenceChangeListener(PlatformUIPreferenceListener.getSingleton());

			}
			@Override
			public void removed(NodeChangeEvent event) {

			}
		});

		JFacePreferences.setPreferenceStore(WorkbenchPlugin.getDefault().getPreferenceStore());
	}
}
