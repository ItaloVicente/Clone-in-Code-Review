package org.eclipse.e4.ui.progress.internal;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.ui.progress.WorkbenchJob;
import org.eclipse.e4.ui.progress.e4new.ExternalServices;
import org.eclipse.jface.dialogs.IconAndMessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class BlockedJobsDialog extends IconAndMessageDialog {
	protected static BlockedJobsDialog singleton;

	private DetailedProgressViewer viewer;

	private String blockedTaskName = ProgressMessages.SubTaskInfo_UndefinedTaskName;

	private Button cancelSelected;

	private Cursor arrowCursor;

	private Cursor waitCursor;

	private IProgressMonitor blockingMonitor;

	private JobTreeElement blockedElement = new BlockedUIElement();

	private class BlockedUIElement extends JobTreeElement {

		Object[] getChildren() {
			return ProgressManagerUtil.EMPTY_OBJECT_ARRAY;
		}

		String getDisplayString() {
			if (blockedTaskName == null || blockedTaskName.length() == 0) {
				return ProgressMessages.BlockedJobsDialog_UserInterfaceTreeElement;
			}
			return blockedTaskName;
		}

		public Image getDisplayImage() {
			return JFaceResources.getImage(ProgressManager.WAITING_JOB_KEY);
		}

		boolean hasChildren() {
			return false;
		}

		boolean isActive() {
			return true;
		}

		boolean isJobInfo() {
			return false;
		}

		public void cancel() {
			blockingMonitor.setCanceled(true);
		}

		public boolean isCancellable() {
			return true;
		}
	}

	public static BlockedJobsDialog createBlockedDialog(Shell parentShell,
			IProgressMonitor blockedMonitor, IStatus reason, String taskName) {
		if (singleton != null) {
			return singleton;
		}
		singleton = new BlockedJobsDialog(parentShell, blockedMonitor, reason);

		if (taskName == null || taskName.length() == 0)
			singleton
					.setBlockedTaskName(ProgressMessages.BlockedJobsDialog_UserInterfaceTreeElement);
		else
			singleton.setBlockedTaskName(taskName);

		if (parentShell == null) {
			WorkbenchJob dialogJob = new WorkbenchJob(
					ProgressMessages.EventLoopProgressMonitor_OpenDialogJobName) {
				public IStatus runInUIThread(IProgressMonitor monitor) {
					if (singleton == null) {
						return Status.CANCEL_STATUS;
					}
					if (ProgressManagerUtil.rescheduleIfModalShellOpen(this)) {
						return Status.CANCEL_STATUS;
					}
					singleton.open();
					return Status.OK_STATUS;
				}
			};
			dialogJob.setSystem(true);
			dialogJob.schedule(ExternalServices.getProgressService()
					.getLongOperationTime());
		} else {
			singleton.open();
		}

		return singleton;
	}

	public static void clear(IProgressMonitor monitor) {
		if (singleton == null) {
			return;
		}
		singleton.close(monitor);

	}

	private BlockedJobsDialog(Shell parentShell, IProgressMonitor blocking,
			IStatus blockingStatus) {
		super(parentShell == null ? ProgressManagerUtil.getDefaultParent()
				: parentShell);
		blockingMonitor = blocking;
		setShellStyle(SWT.BORDER | SWT.TITLE | SWT.APPLICATION_MODAL
				| SWT.RESIZE | SWT.MAX | getDefaultOrientation());
		setBlockOnOpen(false);
		setMessage(blockingStatus.getMessage());
	}

	protected Control createDialogArea(Composite parent) {
		setMessage(message);
		createMessageArea(parent);
		showJobDetails(parent);
		return parent;
	}

	void showJobDetails(Composite parent) {
		viewer = new DetailedProgressViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.BORDER);
		viewer.setComparator(new ViewerComparator() {
			public int compare(Viewer testViewer, Object e1, Object e2) {
				return ((Comparable) e1).compareTo(e2);
			}
		});
		ProgressViewerContentProvider provider = getContentProvider();
		viewer.setContentProvider(provider);
		viewer.setInput(provider);
		viewer.setLabelProvider(new ProgressLabelProvider());
		GridData data = new GridData(GridData.GRAB_HORIZONTAL
				| GridData.GRAB_VERTICAL | GridData.FILL_BOTH);
		data.horizontalSpan = 2;
		int heightHint = convertHeightInCharsToPixels(10);
		data.heightHint = heightHint;
		viewer.getControl().setLayoutData(data);
	}

	private ProgressViewerContentProvider getContentProvider() {
		return new ProgressViewerContentProvider(viewer, true, false) {

			public Object[] getElements(Object inputElement) {
				Object[] elements = super.getElements(inputElement);
				Object[] result = new Object[elements.length + 1];
				System.arraycopy(elements, 0, result, 1, elements.length);
				result[0] = blockedElement;
				return result;
			}
		};
	}

	private void clearCursors() {
		clearCursor(cancelSelected);
		clearCursor(getShell());
		if (arrowCursor != null) {
			arrowCursor.dispose();
		}
		if (waitCursor != null) {
			waitCursor.dispose();
		}
		arrowCursor = null;
		waitCursor = null;
	}

	private void clearCursor(Control control) {
		if (control != null && !control.isDisposed()) {
			control.setCursor(null);
		}
	}

	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(ProgressMessages.BlockedJobsDialog_BlockedTitle);
		if (waitCursor == null) {
			waitCursor = new Cursor(shell.getDisplay(), SWT.CURSOR_WAIT);
		}
		shell.setCursor(waitCursor);
	}

	private void setMessage(String messageString) {
		message = messageString == null ? "" : messageString; //$NON-NLS-1$
		if (messageLabel == null || messageLabel.isDisposed()) {
			return;
		}
		messageLabel.setText(message);
	}

	protected Image getImage() {
		return getInfoImage();
	}

	public IProgressMonitor getProgressMonitor() {
		return blockingMonitor;
	}

	public boolean close(IProgressMonitor monitor) {
		if (blockingMonitor != monitor) {
			return false;
		}
		return close();
	}

	public boolean close() {
		singleton = null;
		clearCursors();
		return super.close();
	}

	protected Control createButtonBar(Composite parent) {
		return parent;
	}

	void setBlockedTaskName(String taskName) {
		this.blockedTaskName = taskName;
	}

}
