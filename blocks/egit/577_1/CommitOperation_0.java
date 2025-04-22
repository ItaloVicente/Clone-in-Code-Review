package org.eclipse.egit.ui.internal.synchronize;

import org.eclipse.compare.structuremergeviewer.IDiffElement;
import org.eclipse.team.ui.synchronize.ISynchronizePageConfiguration;
import org.eclipse.team.ui.synchronize.SynchronizeModelAction;
import org.eclipse.team.ui.synchronize.SynchronizeModelOperation;

class CommitAction extends SynchronizeModelAction {

	CommitAction(ISynchronizePageConfiguration configuration) {
		super("Commit...", configuration);
	}

	@Override
	protected SynchronizeModelOperation getSubscriberOperation(
			ISynchronizePageConfiguration configuration, IDiffElement[] elements) {
		return new CommitOperation(configuration, elements);
	}

}
