
package org.eclipse.egit.ui.internal.actions;

public class ListStashAction extends RepositoryAction {

	public ListStashAction() {
		super(ActionCommands.LIST_STASH, new ListStashActionHandler());
	}
}
