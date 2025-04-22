package org.eclipse.egit.ui.internal.synchronize;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.compare.structuremergeviewer.IDiffElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egit.ui.internal.actions.CommitAction;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.team.ui.synchronize.ISynchronizePageConfiguration;
import org.eclipse.team.ui.synchronize.SynchronizeModelOperation;

class CommitOperation extends SynchronizeModelOperation {

	private ISynchronizePageConfiguration configuration;

	CommitOperation(ISynchronizePageConfiguration configuration,
			IDiffElement[] elements) {
		super(configuration, elements);
		this.configuration = configuration;
	}

	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		final CommitAction action = new CommitAction() {
			@Override
			protected IStructuredSelection getSelection() {
				return new StructuredSelection(getSyncInfoSet().getSyncInfos());
			}
		};

		configuration.getSite().getShell().getDisplay().syncExec(new Runnable() {
			public void run() {
				action.run(null);
			}
		});
	}

}
