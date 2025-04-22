package org.eclipse.ui.internal.ide.addons;

import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IPreferenceConstants;
import org.eclipse.ui.progress.WorkbenchJob;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class SaveAllDirtyPartsAddon {

	private final class DirtyEventHandler implements EventHandler {
		@Override
		public void handleEvent(Event event) {
			if (isAutoSaveActive) {
				Object isDirty = event.getProperty(UIEvents.EventTags.NEW_VALUE);
				if (!(isDirty instanceof Boolean) || !(Boolean) isDirty) {
					removeIdleListenerFromWorkbenchDisplay();
					autoSaveJob.cancel();
				} else {
					autoSaveJob.schedule(autoSaveInterval);
					addIdleListenerToWorkbenchDisplay();
				}
			}
		}
	}

	private final class IdleListener implements Listener {
		@Override
		public void handleEvent(org.eclipse.swt.widgets.Event event) {
			if (autoSaveJob.getState() == Job.SLEEPING) {
				autoSaveJob.cancel();
				autoSaveJob.schedule(autoSaveInterval);
			}
		}
	}

	private final class AutoSaveJob extends WorkbenchJob {

		private AutoSaveJob(String name) {
			super(name);
		}

		@Override
		public IStatus runInUIThread(IProgressMonitor monitor) {
			if (isAutoSaveActive) {
				IWorkbench workbench = PlatformUI.getWorkbench();
				if (workbench != null) {
					IWorkbenchWindow[] windows = workbench.getWorkbenchWindows();
					for (IWorkbenchWindow window : windows) {
						IWorkbenchPage p = window.getActivePage();
						if (p != null && !hasVisibleSubShell(getWorkbenchDisplay())) {
							p.saveAllEditors(false);
						} else {
							this.schedule(autoSaveInterval / 2);
						}
					}
				}
			}
			return Status.OK_STATUS;
		}
	}

	@Inject
	IEventBroker eventBroker;

	private final WorkbenchJob autoSaveJob;

	private final EventHandler dirtyHandler;

	private final Listener idleListener;

	private boolean isAutoSaveActive;

	private long autoSaveInterval;

	@Inject
	@Optional
	public void setAutoSave(
			@SuppressWarnings("restriction") @Preference(value = IPreferenceConstants.SAVE_AUTOMATICALLY, nodePath = "org.eclipse.ui.workbench") boolean autoSave) {
		isAutoSaveActive = autoSave;
		if (isAutoSaveActive) {
			eventBroker.subscribe(UIEvents.Dirtyable.TOPIC_DIRTY, dirtyHandler);
		} else {
			eventBroker.unsubscribe(dirtyHandler);
		}
	}

	@Inject
	@Optional
	public void autoSaveIntervalChanged(
			@SuppressWarnings("restriction") @Preference(value = IPreferenceConstants.SAVE_AUTOMATICALLY_INTERVAL, nodePath = "org.eclipse.ui.workbench") int newInterval) {
		autoSaveInterval = TimeUnit.SECONDS.toMillis(newInterval);
	}

	public SaveAllDirtyPartsAddon() {
		autoSaveJob = new AutoSaveJob("Auto save all editors"); //$NON-NLS-1$
		autoSaveJob.setSystem(true);
		idleListener = new IdleListener();
		dirtyHandler = new DirtyEventHandler();
	}

	private void addIdleListenerToWorkbenchDisplay() {
		Display display = getWorkbenchDisplay();
		if (display != null && !display.isDisposed()) {
			display.addFilter(SWT.KeyUp, idleListener);
			display.addFilter(SWT.MouseUp, idleListener);
		}
	}

	private void removeIdleListenerFromWorkbenchDisplay() {
		Display display = getWorkbenchDisplay();
		if (display != null && !display.isDisposed()) {
			display.removeFilter(SWT.MouseUp, idleListener);
			display.removeFilter(SWT.KeyUp, idleListener);
		}
	}

	@PreDestroy
	private void shutdown() {
		eventBroker.unsubscribe(dirtyHandler);
		autoSaveJob.cancel();

		final Display display = getWorkbenchDisplay();
		if (display != null && !display.isDisposed()) {
			try {
				display.asyncExec(new Runnable() {
					@Override
					public void run() {
						removeIdleListenerFromWorkbenchDisplay();
						autoSaveJob.cancel();
					}
				});
			} catch (SWTException ex) {
			}
		}
	}

	private static boolean hasVisibleSubShell(final Display display) {
		if (display != null && !display.isDisposed()) {
			Shell shell = display.getActiveShell();
			if (shell != null) {
				for (Shell subShell : shell.getShells()) {
					if (subShell.isVisible()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private static Display getWorkbenchDisplay() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench != null) {
			return workbench.getDisplay();
		}
		return null;
	}
}
