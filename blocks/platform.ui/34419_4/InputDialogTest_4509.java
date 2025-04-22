package org.eclipse.jface.tests.dialogs;

import junit.framework.TestCase;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class DialogTest extends TestCase {

	private Dialog dialog;

	@Override
	protected void tearDown() throws Exception {
		if (dialog != null) {
			dialog.close();
			dialog = null;
		}
		super.tearDown();
	}

	public void testButtonAlignmentBug272583() {
		ForceLayoutDialog forceLayoutDialog = new ForceLayoutDialog();
		dialog = forceLayoutDialog;
		forceLayoutDialog.setBlockOnOpen(false);
		forceLayoutDialog.open();

		Button okBtn = forceLayoutDialog.getButton(IDialogConstants.OK_ID);
		Button cancelBtn = forceLayoutDialog
				.getButton(IDialogConstants.CANCEL_ID);

		int okX = okBtn.getBounds().x;
		int cancelX = cancelBtn.getBounds().x;

		if (okBtn.getDisplay().getDismissalAlignment() == SWT.LEFT) {
			assertTrue(
					"The 'OK' button should be to the left of the 'Cancel' button",
					okX < cancelX);
		} else {
			assertTrue(
					"The 'OK' button should be to the right of the 'Cancel' button",
					cancelX < okX);
		}

		forceLayoutDialog.close();
	}

	private class ForceLayoutDialog extends Dialog {

		ForceLayoutDialog() {
			super((Shell) null);
		}

		@Override
		protected Control createContents(Composite parent) {
			Control contents = super.createContents(parent);
			getShell().layout(
					new Control[] { getButton(IDialogConstants.OK_ID) });
			return contents;
		}

		@Override
		protected Button getButton(int id) {
			return super.getButton(id);
		}

	}

}
