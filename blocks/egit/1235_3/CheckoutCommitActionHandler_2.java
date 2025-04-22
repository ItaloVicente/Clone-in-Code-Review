package org.eclipse.egit.ui.internal.actions;

import org.eclipse.egit.ui.internal.commands.SharedCommands;

public class CheckoutCommitAction extends RepositoryAction {
	public CheckoutCommitAction() {
		super(SharedCommands.CHECKOUT, new CheckoutCommitActionHandler());
	}
}
