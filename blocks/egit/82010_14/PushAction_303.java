package org.eclipse.egit.ui.internal.synchronize.action;

import static org.eclipse.egit.ui.internal.synchronize.GitModelSynchronizeParticipant.SYNCHRONIZATION_DATA;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.compare.structuremergeviewer.IDiffElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeData;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeDataSet;
import org.eclipse.egit.ui.internal.pull.PullOperationUI;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.team.ui.synchronize.ISynchronizePageConfiguration;
import org.eclipse.team.ui.synchronize.SynchronizeModelAction;
import org.eclipse.team.ui.synchronize.SynchronizeModelOperation;

public class PullAction extends SynchronizeModelAction {

	public PullAction(String text, ISynchronizePageConfiguration configuration) {
		super(text, configuration);
	}

	@Override
	protected SynchronizeModelOperation getSubscriberOperation(
			ISynchronizePageConfiguration configuration, IDiffElement[] elements) {

		return new SynchronizeModelOperation(configuration, elements) {

			@Override
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				GitSynchronizeDataSet gsds = (GitSynchronizeDataSet) getConfiguration()
						.getProperty(SYNCHRONIZATION_DATA);

				Set<Repository> repositories = new HashSet<>();
				for (GitSynchronizeData gsd : gsds)
					repositories.add(gsd.getRepository());

				PullOperationUI pull = new PullOperationUI(repositories);
				pull.execute(monitor);
			}
		};
	}

	@Override
	public boolean isEnabled() {
		return getConfiguration().getProperty(SYNCHRONIZATION_DATA) != null;
	}

}
