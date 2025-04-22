package org.eclipse.ui.tests.harness.util;

import junit.framework.Assert;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.tests.internal.util.VerifyDialog;

public class DialogCheck {
    private DialogCheck() {
    }

    private static VerifyDialog _verifyDialog;

    public static void assertDialog(Dialog dialog, Assert assertion) {
        Assert.assertNotNull(dialog);
        if (_verifyDialog.getShell() == null) {
            getShell();
        }
        if (_verifyDialog.open(dialog) == IDialogConstants.NO_ID) {
            Assert.assertTrue(_verifyDialog.getFailureText(), false);
        }
    }

    public static void assertDialogTexts(Dialog dialog, Assert assertion) {
        Assert.assertNotNull(dialog);
        dialog.setBlockOnOpen(false);
        dialog.open();
        Shell shell = dialog.getShell();
        verifyCompositeText(shell, assertion);
        dialog.close();
    }

    public static Shell getShell() {
        Shell shell = WorkbenchPlugin.getDefault().getWorkbench()
                .getActiveWorkbenchWindow().getShell();
        _verifyDialog = new VerifyDialog(shell);
        _verifyDialog.create();
        return _verifyDialog.getShell();
    }

    private static void verifyCompositeText(Composite composite,
            Assert assertion) {
        Control children[] = composite.getChildren();
		for (Control child : children) {
            if (child instanceof TabFolder) {
                TabFolder folder = (TabFolder) child;
                int numPages = folder.getItemCount();
                for (int j = 0; j < numPages; j++) {
                    folder.setSelection(j);
                }
            }
            else if (child instanceof Button) {
				verifyButtonText((Button) child);
            }
            else if (child instanceof Label) {
				verifyLabelText((Label) child);
            }
            else if (child instanceof Composite) {
                verifyCompositeText((Composite) child, assertion);
            }
        }
    }

	private static void verifyButtonText(Button button) {
        String widget = button.toString();
        Point size = button.getSize();

        Point preferred = button.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        if (preferred.y * size.y > 0) {
            preferred.y /= countLines(button.getText()); //check for '\n\'
            if (size.y / preferred.y > 1) {
                preferred.x /= (size.y / preferred.y);
            }
        }

        String message = new StringBuffer("Warning: ").append(widget).append(
                "\n\tActual Width -> ").append(size.x).append(
                "\n\tRecommended Width -> ").append(preferred.x).toString();
        if (preferred.x > size.x) {
            button.getShell().dispose();
            Assert.assertTrue(message.toString(), false);
        }
    }

	private static void verifyLabelText(Label label) {
        String widget = label.toString();
        Point size = label.getSize();
        String labelText = label.getText();
        if (labelText == null || labelText.length() == 0)
        	return;
        Point preferred = label.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        if (preferred.y * size.y > 0) {
            preferred.y /= countLines(label.getText());
            if (size.y / preferred.y > 1) {
                preferred.x /= (size.y / preferred.y);
            }
        }
        String message = new StringBuffer("Warning: ").append(widget).append(
                "\n\tActual Width -> ").append(size.x).append(
                "\n\tRecommended Width -> ").append(preferred.x).toString();
        if (preferred.x > size.x) {
            label.getShell().dispose();
            Assert.assertTrue(message.toString(), false);
        }
    }

    private static int countLines(String text) {
        int newLines = 1;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '\n') {
                newLines++;
            }
        }
        return newLines;
    }
}
