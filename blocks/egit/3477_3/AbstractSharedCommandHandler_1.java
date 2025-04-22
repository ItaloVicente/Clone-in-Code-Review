package org.eclipse.egit.ui.internal.actions;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.core.op.RebaseOperation;
import org.eclipse.egit.ui.internal.commands.shared.RebaseCurrentRefCommand;

public class RebaseActionHandler extends RepositoryActionHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		return new RebaseCurrentRefCommand().execute(event);
	}

	@Override
	public boolean isEnabled() {
		return getRepository() != null && containsHead();
	}
}
