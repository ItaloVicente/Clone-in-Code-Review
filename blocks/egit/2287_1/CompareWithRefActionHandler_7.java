
package org.eclipse.egit.ui.internal.actions;

public class CompareWithRefAction extends RepositoryAction {
	public CompareWithRefAction() {
		super(ActionCommands.COMPARE_WITH_REF_ACTION,
				new CompareWithRefActionHandler());
	}
}
