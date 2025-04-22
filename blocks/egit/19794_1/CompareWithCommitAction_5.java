package org.eclipse.egit.ui.internal.actions;

public class CompareIndexWithHeadAction extends RepositoryAction {

	public CompareIndexWithHeadAction() {
		super(ActionCommands.COMPARE_INDEX_WITH_HEAD_ACTION, new CompareIndexWithHeadActionHandler());
	}
}
