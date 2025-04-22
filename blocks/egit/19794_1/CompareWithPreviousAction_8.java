
package org.eclipse.egit.ui.internal.actions;

public class CompareWithIndexAction extends RepositoryAction {
	public CompareWithIndexAction() {
		super(ActionCommands.COMPARE_WITH_INDEX_ACTION, new CompareWithIndexActionHandler());
	}
}
