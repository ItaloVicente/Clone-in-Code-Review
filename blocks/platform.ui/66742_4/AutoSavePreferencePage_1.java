package org.eclipse.ui.internal.ide.addons;

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
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.progress.WorkbenchJob;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class SaveAllDirtyPartsAddon {

	@Inject
	IEventBroker eventBroker;

	private WorkbenchJob saveJob;

	private EventHandler dirtyHandler;

	final private Listener idleListener;

	final private Runnable saveJobScheduler;

	@Inject
	@Optional
	public void setAutoSave(
			@SuppressWarnings("restriction") @Preference(value = IPreferenceConstants.SAVE_AUTOMATICALLY, nodePath = "org.eclipse.ui.workbench") boolean autoSave) {
		if (autoSave) {
			eventBroker.subscribe(UIEvents.Dirtyable.TOPIC_DIRTY, dirtyHandler);
		} else {
			eventBroker.unsubscribe(dirtyHandler);
			shutdown();
		}
	}

	public SaveAllDirtyPartsAddon() {
		saveJob = new WorkbenchJob("Auto save parts") { //$NON-NLS-1$
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				if (WorkbenchPlugin.getDefault().getPreferenceStore()
						.getBoolean(IPreferenceConstants.SAVE_AUTOMATICALLY)) {
					IWorkbench workbench = PlatformUI.getWorkbench();
					if (workbench != null) {
						IWorkbenchWindow[] windows = workbench.getWorkbenchWindows();
						for (IWorkbenchWindow window : windows) {
							IWorkbenchPage p = window.getActivePage();
							Display display = getWorkbenchDisplay();
							if (p != null && !hasSubShellActive(display)) {
								p.saveAllEditors(false);
							}
						}
					}
				}
				return Status.OK_STATUS;
			}
		};
		saveJobScheduler = new Runnable() {

			@Override
			public void run() {
				if (saveJob.getState() != Job.RUNNING) {
					saveJob.schedule();
				}
				Display display = getWorkbenchDisplay();
				if (display != null) {
					display.timerExec(WorkbenchPlugin.getDefault().getPreferenceStore()
							.getInt(IPreferenceConstants.SAVE_AUTOMATICALLY_INTERVAL) * 1000, this);
				}
			}
		};
		idleListener = new Listener() {
			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				Display display = getWorkbenchDisplay();
				if (display != null) {
					display.timerExec(WorkbenchPlugin.getDefault().getPreferenceStore()
							.getInt(IPreferenceConstants.SAVE_AUTOMATICALLY_INTERVAL) * 1000, saveJobScheduler);
				}
			}
		};
		dirtyHandler = new EventHandler() {
			@Override
			public void handleEvent(Event event) {
				if (WorkbenchPlugin.getDefault().getPreferenceStore()
						.getBoolean(IPreferenceConstants.SAVE_AUTOMATICALLY)) {
					Object newValue = event.getProperty(UIEvents.EventTags.NEW_VALUE);
					if (!(newValue instanceof Boolean)) {
						return;
					}
					boolean dirty = (Boolean) newValue;
					if (!dirty) {
						return;
					}
					Display display = getWorkbenchDisplay();
					display.addFilter(SWT.KeyUp, idleListener);
					display.addFilter(SWT.MouseUp, idleListener);
				}
			}
		};
	}

	@PreDestroy
	private void shutdown() {
		if (saveJob != null) {
			saveJob.cancel();
		}
		if (idleListener == null) {
			return;
		}
		Display display = getWorkbenchDisplay();
		if (display != null && !display.isDisposed()) {
			try {
				display.asyncExec(new Runnable() {
					@Override
					public void run() {
						display.removeFilter(SWT.KeyUp, idleListener);
						display.removeFilter(SWT.MouseUp, idleListener);
					}
				});
			} catch (SWTException ex) {
			}
		}
	}

	private boolean hasSubShellActive(final Display display) {
		if (display != null) {
			Shell shell = display.getActiveShell();
			if (shell != null && shell.isVisible() && shell.isEnabled()) {
				for (Shell subShell : shell.getShells()) {
					if (subShell.isVisible() && subShell.isEnabled()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private Display getWorkbenchDisplay() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench != null) {
			final Display display = workbench.getDisplay();
			if (display != null && !display.isDisposed()) {
				return display;
			}
		}
		return null;
	}
}
