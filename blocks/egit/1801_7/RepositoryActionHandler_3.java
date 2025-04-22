
package org.eclipse.egit.ui.internal.actions;

import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.CompareUI;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.egit.ui.internal.merge.GitMergeEditorInput;
import org.eclipse.egit.ui.internal.merge.MergeModeDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jgit.lib.Repository;

public class MergeToolActionHandler extends RepositoryActionHandler {

	public Object execute(final ExecutionEvent event) throws ExecutionException {
		int mergeMode = Activator.getDefault().getPreferenceStore().getInt(
				UIPreferences.MERGE_MODE);
		CompareEditorInput input;
		if (mergeMode == 0) {
			MergeModeDialog dlg = new MergeModeDialog(getShell(event));
			if (dlg.open() != Window.OK)
				return null;
			input = new GitMergeEditorInput(dlg.useWorkspace(),
					getSelectedResources(event));
		} else {
			boolean useWorkspace = mergeMode == 1;
			input = new GitMergeEditorInput(useWorkspace,
					getSelectedResources(event));
		}
		CompareUI.openCompareEditor(input);
		return null;
	}

	public boolean isEnabled() {
		Repository[] repos = getRepositoriesFor(getProjectsForSelectedResources());
		if (repos.length != 1)
			return false;
		switch (repos[0].getRepositoryState()) {
		case MERGING:
		case REBASING:
		case REBASING_INTERACTIVE:
		case REBASING_MERGE:
		case REBASING_REBASING:
			return true;
		default:
			return false;
		}
	}
}
