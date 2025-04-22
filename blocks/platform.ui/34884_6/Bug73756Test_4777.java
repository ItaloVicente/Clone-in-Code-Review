package org.eclipse.ui.tests.commands;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.actions.RetargetAction;
import org.eclipse.ui.commands.ActionHandler;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class Bug70503Test extends UITestCase {

	private class PubliclyRetargettableAction extends RetargetAction {
		public PubliclyRetargettableAction(String actionID, String text) {
			super(actionID, text);
		}

		private final void changeHandler(final IAction handler) {
			super.setActionHandler(handler);
		}
	}

	public Bug70503Test(String name) {
		super(name);
	}

	public final void testHandlerChangeCausesUpdate() {
		final PubliclyRetargettableAction retargetAction = new PubliclyRetargettableAction(
				"actionID", "text");
		final ActionHandler actionHandler = new ActionHandler(retargetAction);
		assertFalse("The retarget action handler should start 'unhandled'",
				((Boolean) actionHandler.getAttributeValuesByName().get(
						"handled")).booleanValue());
		retargetAction.changeHandler(new PubliclyRetargettableAction(
				"actionID", "text"));
		assertTrue(
				"The retarget action handler should recognize the new handler.",
				((Boolean) actionHandler.getAttributeValuesByName().get(
						"handled")).booleanValue());
		retargetAction.changeHandler(null);
		assertFalse(
				"The retarget action handler should recognize that the handler is now gone.",
				((Boolean) actionHandler.getAttributeValuesByName().get(
						"handled")).booleanValue());
	}
}
