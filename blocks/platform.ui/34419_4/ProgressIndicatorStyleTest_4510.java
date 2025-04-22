package org.eclipse.jface.tests.dialogs;

import junit.framework.TestCase;

import org.eclipse.jface.dialogs.InputDialog;

public class InputDialogTest extends TestCase {

	private InputDialog dialog;

	@Override
	protected void tearDown() throws Exception {
		if (dialog != null) {
			dialog.close();
			dialog = null;
		}
		super.tearDown();
	}

	public void testSetErrorMessageEarly() {
		dialog = new InputDialog(null, "TEST", "value", "test", null);
		dialog.setBlockOnOpen(false);
		dialog.setErrorMessage("error");
		dialog.open();
	}
}
