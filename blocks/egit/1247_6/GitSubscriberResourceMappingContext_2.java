package org.eclipse.egit.core.synchronize;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeDataSet;
import org.eclipse.team.core.diff.IDiff;
import org.eclipse.team.core.mapping.ISynchronizationScopeManager;
import org.eclipse.team.core.subscribers.Subscriber;
import org.eclipse.team.core.subscribers.SubscriberMergeContext;

public class GitSubscriberMergeContext extends SubscriberMergeContext {

	private final GitSynchronizeDataSet gsds;

	public GitSubscriberMergeContext(Subscriber subscriber,
			ISynchronizationScopeManager manager, GitSynchronizeDataSet gsds) {
		super(subscriber, manager);
		this.gsds = gsds;

		initialize();
	}

	public void markAsMerged(IDiff node, boolean inSyncHint,
			IProgressMonitor monitor) throws CoreException {

	}

	public void reject(IDiff diff, IProgressMonitor monitor)
			throws CoreException {

	}

	public GitSynchronizeDataSet getSyncData() {
		return gsds;
	}

	@Override
	protected void makeInSync(IDiff diff, IProgressMonitor monitor)
			throws CoreException {

	}

}
