package org.eclipse.egit.ui.internal.jobs;

import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.egit.core.op.MergingOperation;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.egit.ui.internal.dialogs.PreferredStrategyDialog;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jface.window.Window;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.swt.widgets.Display;

public class MergeJob extends WorkspaceJob {

	private final MergingOperation operation;

	public MergeJob(String name, @NonNull MergingOperation op) {
		super(name);
		this.operation = op;
	}

	@Override
	public final IStatus runInWorkspace(IProgressMonitor monitor)
			throws CoreException {
		try {
			operation.setMergeStrategy(obtainMergeStrategy());
		} catch (OperationCanceledException e) {
			return Status.CANCEL_STATUS;
		}
		return doRunInWorkspace(monitor);
	}

	protected MergeStrategy obtainMergeStrategy()
			throws OperationCanceledException {
		IEclipsePreferences uiPrefs = InstanceScope.INSTANCE.getNode(Activator
				.getPluginId());
		if (!uiPrefs.getBoolean(
				UIPreferences.PREFERRED_MERGE_STRATEGY_HIDE_DIALOG, false)) {
			SelectMergeStrategy select = new SelectMergeStrategy();
			select.displaySelectionDialog();
			return select.getStrategy();
		}
		return org.eclipse.egit.core.Activator.getDefault()
				.getPreferredMergeStrategy();
	}

	protected IStatus doRunInWorkspace(IProgressMonitor monitor) {
		try {
			operation.execute(monitor);
		} catch (final CoreException e) {
			return e.getStatus();
		}
		return Status.OK_STATUS;
	}

	private static class SelectMergeStrategy {

		private boolean canceled;

		private MergeStrategy strategy;

		private MergeStrategy getStrategy() {
			return strategy;
		}

		protected void displaySelectionDialog()
				throws OperationCanceledException {
			Display.getDefault().syncExec(new Runnable() {
				@Override
				public void run() {
					PreferredStrategyDialog dialog = new PreferredStrategyDialog(
							Display.getDefault().getActiveShell());
					if (dialog.open() == Window.CANCEL) {
						cancelOperation();
					} else {
						setStrategy(dialog.getSelectedStrategy());
					}
				}
			});
			if (canceled) {
				throw new OperationCanceledException();
			}
		}

		private void cancelOperation() {
			this.canceled = true;
		}

		private void setStrategy(MergeStrategy strategy) {
			this.strategy = strategy;
		}
	}

}
