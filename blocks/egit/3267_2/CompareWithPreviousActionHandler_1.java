package org.eclipse.egit.ui.internal.actions;

public class CompareWithPreviousAction extends RepositoryAction {

	public CompareWithPreviousAction() {
		super(ActionCommands.COMPARE_WITH_PREVIOUS_ACTION,
				new CompareWithPreviousActionHandler());
	}

}
