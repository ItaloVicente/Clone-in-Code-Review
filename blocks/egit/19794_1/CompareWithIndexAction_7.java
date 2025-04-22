package org.eclipse.egit.ui.internal.actions;

public class CompareWithHeadAction extends RepositoryAction {

	public CompareWithHeadAction() {
		super(ActionCommands.COMPARE_WITH_HEAD_ACTION, new CompareWithHeadActionHandler());
	}
}
