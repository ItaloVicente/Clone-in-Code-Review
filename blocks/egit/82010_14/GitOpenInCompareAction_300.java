package org.eclipse.egit.ui.internal.synchronize.action;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.compare.structuremergeviewer.IDiffElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.team.ui.synchronize.ISynchronizePageConfiguration;
import org.eclipse.team.ui.synchronize.SynchronizeModelAction;
import org.eclipse.team.ui.synchronize.SynchronizeModelOperation;

public class ExpandAllModelAction extends SynchronizeModelAction {

	private static final class ExpandAllAction implements Runnable {
		private final ISynchronizePageConfiguration configuration;

		private ExpandAllAction(ISynchronizePageConfiguration configuration) {
			this.configuration = configuration;
		}

		@Override
		public void run() {
			Viewer viewer = configuration.getPage().getViewer();
			if (viewer == null || viewer.getControl().isDisposed()
					|| !(viewer instanceof AbstractTreeViewer)) {
				return;
			}
			viewer.getControl().setRedraw(false);
			((AbstractTreeViewer) viewer).expandAll();
			viewer.getControl().setRedraw(true);
		}
	}

	public ExpandAllModelAction(String text,
			ISynchronizePageConfiguration configuration) {
		super(text, configuration);
	}

	@Override
	protected SynchronizeModelOperation getSubscriberOperation(
			final ISynchronizePageConfiguration configuration,
			IDiffElement[] elements) {
		return new SynchronizeModelOperation(configuration, elements) {

			@Override
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				configuration.getSite().getShell().getDisplay()
						.syncExec(new ExpandAllAction(configuration));
			}
		};
	}

	@Override
	protected boolean updateSelection(IStructuredSelection selection) {
		return true;
	}

}
