
package org.eclipse.egit.ui.internal.history.command;

import org.eclipse.egit.core.op.RebaseOperation;
import org.eclipse.egit.ui.internal.branch.LaunchFinder;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;

public class RebaseCurrentHandler extends AbstractRebaseHistoryCommandHandler {

	@Override
	protected RebaseOperation createRebaseOperation(Repository repository,
			Ref ref) {
		if (LaunchFinder.shouldCancelBecauseOfRunningLaunches(repository,
				null)) {
			return null;
		}
		return new RebaseOperation(repository, ref);
	}

}
