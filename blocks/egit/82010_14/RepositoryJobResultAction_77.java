package org.eclipse.egit.ui.internal.jobs;

import java.text.MessageFormat;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jface.action.IAction;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressConstants;

public abstract class RepositoryJob extends Job {

	public RepositoryJob(String name) {
		super(name);
	}

	@Override
	protected final IStatus run(IProgressMonitor monitor) {
		try {
			IStatus status = performJob(monitor);
			if (status == null) {
				return Activator.createErrorStatus(MessageFormat
						.format(UIText.RepositoryJob_NullStatus, getName()),
						new NullPointerException());
			} else if (!status.isOK()) {
				return status;
			}
			IAction action = getAction();
			if (action != null) {
				if (isModal()) {
					showResult(action);
				} else {
					setProperty(IProgressConstants.KEEP_PROPERTY, Boolean.TRUE);
					setProperty(IProgressConstants.ACTION_PROPERTY, action);
					IStatus finalStatus = getDeferredStatus();
					String msg = finalStatus.getMessage();
					if (msg == null || msg.isEmpty()) {
						return new Status(finalStatus.getSeverity(),
								finalStatus.getPlugin(), finalStatus.getCode(),
								action.getText(), finalStatus.getException());
					}
					return finalStatus;
				}
			}
			return status;
		} finally {
			monitor.done();
		}
	}

	abstract protected IStatus performJob(IProgressMonitor monitor);

	abstract protected IAction getAction();

	@NonNull
	protected IStatus getDeferredStatus() {
		return new Status(IStatus.OK, Activator.getPluginId(), IStatus.OK, "", //$NON-NLS-1$
				null);
	}

	private boolean isModal() {
		Boolean modal = (Boolean) getProperty(
				IProgressConstants.PROPERTY_IN_DIALOG);
		return modal != null && modal.booleanValue();
	}

	private void showResult(final IAction action) {
		final Display display = PlatformUI.getWorkbench().getDisplay();
		if (display != null) {
			display.asyncExec(new Runnable() {

				@Override
				public void run() {
					if (!display.isDisposed()) {
						action.run();
					}
				}
			});
		}
	}
}
