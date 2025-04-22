
package org.eclipse.egit.ui.internal.history.command;

import org.eclipse.egit.core.op.RebaseOperation;
import org.eclipse.egit.ui.internal.rebase.RebaseInteractiveHandler;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;


public class RebaseInteractiveCurrentHandler extends AbstractRebaseHistoryCommandHandler {

	@Override
	protected RebaseOperation createRebaseOperation(Repository repository,
			Ref ref) {
		return new RebaseOperation(repository, ref,
				RebaseInteractiveHandler.INSTANCE);
	}
}
