
package org.eclipse.egit.ui.internal.actions;

public class CompareWithCommitAction extends RepositoryAction {
	public CompareWithCommitAction() {
		super(ActionCommands.COMPARE_WITH_COMMIT_ACTION,
				new CompareWithCommitActionHandler());
	}
}
