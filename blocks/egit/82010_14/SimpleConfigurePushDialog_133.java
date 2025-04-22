package org.eclipse.egit.ui.internal.push;

import org.eclipse.egit.core.op.PushOperationResult;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.jobs.RepositoryJobResultAction;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class ShowPushResultAction extends RepositoryJobResultAction {

	private final boolean showConfigure;

	private final PushOperationResult operationResult;

	private final String destination;

	public ShowPushResultAction(@NonNull Repository repository,
			PushOperationResult result, String destination,
			boolean showConfigureButton) {
		super(repository, UIText.ShowPushResultAction_name);
		this.operationResult = result;
		this.destination = destination;
		this.showConfigure = showConfigureButton;
	}

	private boolean isModal(Shell shell) {
		return (shell.getStyle() & (SWT.APPLICATION_MODAL | SWT.PRIMARY_MODAL
				| SWT.SYSTEM_MODAL)) != 0;
	}

	@Override
	protected void showResult(@NonNull Repository repository) {
		Shell shell = PlatformUI.getWorkbench().getModalDialogShellProvider()
				.getShell();
		PushResultDialog dialog = new PushResultDialog(shell, repository,
				operationResult, destination, isModal(shell));
		dialog.showConfigureButton(showConfigure);
		dialog.open();
	}
}
