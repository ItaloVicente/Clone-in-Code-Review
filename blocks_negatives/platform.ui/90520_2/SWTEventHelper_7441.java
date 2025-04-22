/*******************************************************************************
 * Copyright (c) 2004, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.tests.harness.util;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.DisplayAccess;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;

/**
 * This advisor adds the ability to exit the workbench after it has started up.
 * This is done with a call to close from within the advisor's event idle loop.
 * The number of times the idle is called before exiting can be configured. Test
 * cases should subclass this advisor and add their own callback methods if
 * needed.
 *
 * @since 3.1
 */
public class RCPTestWorkbenchAdvisor extends WorkbenchAdvisor {

	public static Boolean asyncDuringStartup = null;

	public static volatile Boolean syncWithDisplayAccess = null;
	public static volatile Boolean asyncWithDisplayAccess = null;
	public static volatile Boolean syncWithoutDisplayAccess = null;
	public static volatile Boolean asyncWithoutDisplayAccess = null;

	private static boolean started = false;

	public static boolean isSTARTED() {
		synchronized (RCPTestWorkbenchAdvisor.class) {
			return started;
		}
	}

	/** Default value of -1 causes the option to be ignored. */
	private int idleBeforeExit = -1;

	private boolean windowlessApp = false;

	/**
	 * Traps whether or not calls to displayAccess in the UI thread resulted in
	 * an exception. Should be false.
	 */
	public static boolean displayAccessInUIThreadAllowed;

	public RCPTestWorkbenchAdvisor() {
		this.idleBeforeExit = -1;
	}

	public RCPTestWorkbenchAdvisor(int idleBeforeExit) {
		this.idleBeforeExit = idleBeforeExit;
	}

	/**
	 *
	 * Enables the RCP application to runwithout a workbench window
	 *
	 * @param runWithoutWindow
	 *
	 */
	public RCPTestWorkbenchAdvisor(boolean windowlessApp) {
		this.windowlessApp = windowlessApp;
	}

	@Override
	public void initialize(IWorkbenchConfigurer configurer) {
		super.initialize(configurer);

		IPreferenceStore prefs = PlatformUI.getPreferenceStore();
		prefs
				.setValue(IWorkbenchPreferenceConstants.DEFAULT_PERSPECTIVE_ID,
						"");
		prefs.setValue(IWorkbenchPreferenceConstants.SHOW_PROGRESS_ON_STARTUP,
				false);
		prefs.setValue(IWorkbenchPreferenceConstants.SHOW_INTRO, false);

		if(windowlessApp) {
			configurer.setSaveAndRestore(true);
			configurer.setExitOnLastWindowClose(false);
		}

	}

	@Override
	public String getInitialWindowPerspectiveId() {
		return EmptyPerspective.PERSP_ID;
	}

	@Override
	public void eventLoopIdle(final Display display) {
		if (idleBeforeExit != -1 && --idleBeforeExit <= 0)
			PlatformUI.getWorkbench().close();


		if (idleBeforeExit == -1)
			return;
	}

	@Override
	public void preStartup() {
		super.preStartup();
		final Display display = Display.getCurrent();
		if (display != null) {
			display.asyncExec(new Runnable() {

				@Override
				public void run() {
					if (isSTARTED())
						asyncDuringStartup = Boolean.FALSE;
					else
						asyncDuringStartup = Boolean.TRUE;
				}
			});
		}


		setupAsyncDisplayThread(true, display);
		setupSyncDisplayThread(true, display);
		setupAsyncDisplayThread(false, display);
		setupSyncDisplayThread(false, display);

		try {
			DisplayAccess.accessDisplayDuringStartup();
			displayAccessInUIThreadAllowed = true;
		}
		catch (IllegalStateException e) {
			displayAccessInUIThreadAllowed = false;
		}
	}

	/**
	 * @param display
	 */
	private void setupSyncDisplayThread(final boolean callDisplayAccess, final Display display) {
		Thread syncThread = new Thread() {
			@Override
			public void run() {
				if (callDisplayAccess)
					DisplayAccess.accessDisplayDuringStartup();
				try {
					display.syncExec(new Runnable() {
						@Override
						public void run() {
							synchronized (RCPTestWorkbenchAdvisor.class) {
								if (callDisplayAccess)
									syncWithDisplayAccess = !isSTARTED() ? Boolean.TRUE
											: Boolean.FALSE;
								else
									syncWithoutDisplayAccess = !isSTARTED() ? Boolean.TRUE
											: Boolean.FALSE;
							}
						}
					});
				} catch (SWTException e) {
				}
			}
		};
		syncThread.setDaemon(true);
		syncThread.start();
	}

	/**
	 * @param display
	 */
	private void setupAsyncDisplayThread(final boolean callDisplayAccess, final Display display) {
		Thread asyncThread = new Thread() {
			@Override
			public void run() {
				if (callDisplayAccess)
					DisplayAccess.accessDisplayDuringStartup();
				display.asyncExec(new Runnable() {
					@Override
					public void run() {
						synchronized (RCPTestWorkbenchAdvisor.class) {
							if (callDisplayAccess)
								asyncWithDisplayAccess = !isSTARTED() ? Boolean.TRUE
										: Boolean.FALSE;
							else
								asyncWithoutDisplayAccess = !isSTARTED() ? Boolean.TRUE
										: Boolean.FALSE;
						}
					}});
			}
		};
		asyncThread.setDaemon(true);
		asyncThread.start();
	}

	@Override
	public void postStartup() {
		super.postStartup();
		synchronized (RCPTestWorkbenchAdvisor.class) {
			started = true;
		}
	}
}
