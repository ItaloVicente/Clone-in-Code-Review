
package org.eclipse.ui.application;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.internal.StartupThreading;
import org.eclipse.ui.internal.StartupThreading.StartupRunnable;
import org.eclipse.ui.internal.UISynchronizer;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.WorkbenchWindowConfigurer;
import org.eclipse.ui.internal.application.CompatibilityWorkbenchWindowAdvisor;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.model.ContributionComparator;
import org.eclipse.ui.model.IContributionService;
import org.eclipse.ui.statushandlers.AbstractStatusHandler;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.statushandlers.WorkbenchErrorHandler;

public abstract class WorkbenchAdvisor {

	@Deprecated
	public static final int FILL_PROXY = ActionBarAdvisor.FILL_PROXY;

	@Deprecated
	public static final int FILL_MENU_BAR = ActionBarAdvisor.FILL_MENU_BAR;

	@Deprecated
	public static final int FILL_COOL_BAR = ActionBarAdvisor.FILL_COOL_BAR;

	@Deprecated
	public static final int FILL_STATUS_LINE = ActionBarAdvisor.FILL_STATUS_LINE;

	private IWorkbenchConfigurer workbenchConfigurer;

	private AbstractStatusHandler workbenchErrorHandler;

	private boolean introOpened;

	protected WorkbenchAdvisor() {
	}

	public final void internalBasicInitialize(IWorkbenchConfigurer configurer) {
		if (workbenchConfigurer != null) {
			throw new IllegalStateException();
		}
		this.workbenchConfigurer = configurer;
		initialize(configurer);
	}

	public void initialize(IWorkbenchConfigurer configurer) {
	}

	protected IWorkbenchConfigurer getWorkbenchConfigurer() {
		return workbenchConfigurer;
	}

	public synchronized AbstractStatusHandler getWorkbenchErrorHandler() {
		if (workbenchErrorHandler == null) {
			workbenchErrorHandler = new WorkbenchErrorHandler();
		}
		return workbenchErrorHandler;
	}

	public void preStartup() {
	}

	public void postStartup() {
	}

	public boolean preShutdown() {
		return true;
	}

	public void postShutdown() {
	}

	public void eventLoopException(Throwable exception) {
		if (exception == null) {
			return;
		}

		try {
			StatusManager.getManager().handle(
					new Status(IStatus.ERROR, WorkbenchPlugin.PI_WORKBENCH,
							"Unhandled event loop exception", exception)); //$NON-NLS-1$

			if (WorkbenchPlugin.DEBUG) {
				exception.printStackTrace();
			}
		} catch (Throwable e) {
			System.err.println("Error while logging event loop exception:"); //$NON-NLS-1$
			exception.printStackTrace();
			System.err.println("Logging exception:"); //$NON-NLS-1$
			e.printStackTrace();
		}
	}

	public void eventLoopIdle(Display display) {
		display.sleep();
	}

	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		return new CompatibilityWorkbenchWindowAdvisor(this, configurer);
	}

	@Deprecated
	public void preWindowOpen(IWorkbenchWindowConfigurer configurer) {
	}

	@Deprecated
	public void fillActionBars(IWorkbenchWindow window,
			IActionBarConfigurer configurer, int flags) {
	}

	@Deprecated
	public void postWindowRestore(IWorkbenchWindowConfigurer configurer)
			throws WorkbenchException {
	}

	@Deprecated
	public void openIntro(IWorkbenchWindowConfigurer configurer) {
		if (introOpened) {
			return;
		}

		introOpened = true;

		boolean showIntro = PrefUtil.getAPIPreferenceStore().getBoolean(
				IWorkbenchPreferenceConstants.SHOW_INTRO);

		if (!showIntro) {
			return;
		}

		if (getWorkbenchConfigurer().getWorkbench().getIntroManager()
				.hasIntro()) {
			PrefUtil.getAPIPreferenceStore().setValue(
					IWorkbenchPreferenceConstants.SHOW_INTRO, false);
			PrefUtil.saveAPIPrefs();
			
			getWorkbenchConfigurer().getWorkbench().getIntroManager()
					.showIntro(configurer.getWindow(), false);
		}
	}

	@Deprecated
	public void postWindowCreate(IWorkbenchWindowConfigurer configurer) {
	}

	@Deprecated
	public void postWindowOpen(IWorkbenchWindowConfigurer configurer) {
	}

	@Deprecated
	public boolean preWindowShellClose(IWorkbenchWindowConfigurer configurer) {
		return true;
	}

	@Deprecated
	public void postWindowClose(IWorkbenchWindowConfigurer configurer) {
	}

	@Deprecated
	public boolean isApplicationMenu(IWorkbenchWindowConfigurer configurer,
			String menuId) {
		return false;
	}

	public IAdaptable getDefaultPageInput() {
		return null;
	}

	public abstract String getInitialWindowPerspectiveId();

	public String getMainPreferencePageId() {
		return null;
	}

	@Deprecated
	public void createWindowContents(IWorkbenchWindowConfigurer configurer,
			Shell shell) {
		((WorkbenchWindowConfigurer) configurer).createDefaultContents(shell);
	}

	private volatile boolean initDone = false;

	public boolean openWindows() {
		final Display display = PlatformUI.getWorkbench().getDisplay();
		final boolean result [] = new boolean[1];
		

		final Throwable [] error = new Throwable[1];
		Thread initThread = new Thread() {
			@Override
			public void run() {
				try {
					UISynchronizer.startupThread.set(Boolean.TRUE);
					final IWorkbenchConfigurer [] myConfigurer = new IWorkbenchConfigurer[1];
					StartupThreading.runWithoutExceptions(new StartupRunnable() {
	
						@Override
						public void runWithException() throws Throwable {
							myConfigurer[0] = getWorkbenchConfigurer();
							
						}});
					
					IStatus status = myConfigurer[0].restoreState();
					if (!status.isOK()) {
						if (status.getCode() == IWorkbenchConfigurer.RESTORE_CODE_EXIT) {
							result[0] = false;
							return;
						}
						if (status.getCode() == IWorkbenchConfigurer.RESTORE_CODE_RESET) {
							myConfigurer[0].openFirstTimeWindow();
						}
					}
					result[0] = true;
				} catch (Throwable e) {
					error[0] = e;
				}
				finally {
					initDone = true;
					yield();
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
					}
					display.wake();
				}
			}};
			initThread.start();

		while (true) {
			if (!display.readAndDispatch()) {
				if (initDone)
					break;
				display.sleep();
			}

		}
			
			if (error[0] instanceof Error)
				throw (Error)error[0];
			else if (error[0] instanceof RuntimeException)
				throw (RuntimeException)error[0];
		
			return result[0];
	}

	public IStatus saveState(IMemento memento) {
		return Status.OK_STATUS;
	}

	public IStatus restoreState(IMemento memento) {
		return Status.OK_STATUS;
	}

	public ContributionComparator getComparatorFor(String contributionType) {
		return new ContributionComparator();
	}
}
