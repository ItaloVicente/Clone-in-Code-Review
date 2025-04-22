package org.eclipse.egit.ui.internal.actions;

public class MergeToolAction extends RepositoryAction {
	public MergeToolAction() {
		super(ActionCommands.MERGE_TOOL_ACTION, new MergeToolActionHandler());
	}
}
