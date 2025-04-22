package org.eclipse.egit.gitflow.ui.internal.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.egit.gitflow.ui.Activator;
import org.eclipse.egit.gitflow.ui.internal.UIText;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.RebaseResult;
import org.eclipse.osgi.util.NLS;

public abstract class AbstractGitFlowHandler extends AbstractHandler {
	protected MultiStatus createMergeConflictInfo(String develop, String branch) {
		String pluginId = Activator.getPluginId();
		MultiStatus info = new MultiStatus(pluginId, 1,
				UIText.AbstractGitFlowHandler_finishConflicts, null);
		info.add(new Status(IStatus.WARNING, pluginId, 1, NLS.bind(
				UIText.FinishHandler_conflictsWhileMergingFromTo,
				branch, develop), null));

		return info;
	}

	protected MultiStatus createMergeConflictWarning(MergeResult mergeResult) {
		Iterable<String> paths = mergeResult.getConflicts().keySet();
		return docreateConflictWarning(paths,
				UIText.AbstractGitFlowHandler_finishConflicts);
	}

	protected MultiStatus createRebaseConflictWarning(RebaseResult rebaseResult) {
		Iterable<String> paths = rebaseResult.getConflicts();
		return docreateConflictWarning(paths,
				UIText.AbstractGitFlowHandler_rebaseConflicts);
	}

	private MultiStatus docreateConflictWarning(Iterable<String> paths,
			String message) {
		String pluginId = Activator.getPluginId();
		MultiStatus multiStatus = new MultiStatus(pluginId, 1, message, null);
		for (String path : paths) {
			multiStatus.add(new Status(IStatus.WARNING, pluginId, 1, path, null));
		}

		return multiStatus;
	}
}
