package org.eclipse.egit.ui.internal.actions;

import org.eclipse.egit.ui.internal.actions.DiscardChangesActionHandler.Replace;

public class CheckoutResourcesFromHEADAction extends RepositoryAction {
	public CheckoutResourcesFromHEADAction() {
		super(ActionCommands.CHECKOUT_FROM_HEAD_ACTION, new DiscardChangesActionHandler(Replace.HEAD));
	}
}
