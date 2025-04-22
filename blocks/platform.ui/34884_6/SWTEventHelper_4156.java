package org.eclipse.ui.tests.harness.util;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.DisplayAccess;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;

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

	private int idleBeforeExit = -1;

	private boolean windowlessApp = false;

	public static boolean displayAccessInUIThreadAllowed;

	public RCPTestWorkbenchAdvisor() {
		this.idleBeforeExit = -1;
	}

	public RCPTestWorkbenchAdvisor(int idleBeforeExit) {
		this.idleBeforeExit = idleBeforeExit;
	}

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
