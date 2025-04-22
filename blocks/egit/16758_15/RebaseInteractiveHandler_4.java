package org.eclipse.egit.ui.internal.rebase;

import java.util.List;

import org.eclipse.jgit.api.RebaseCommand.InteractiveHandler;
import org.eclipse.jgit.lib.RebaseTodoLine;

public enum RebaseInteractiveHandler implements InteractiveHandler {
	INSTANCE;

	public String modifyCommitMessage(String commit) {
		return commit;
	}

	public void prepareSteps(List<RebaseTodoLine> steps) {
	}
}
