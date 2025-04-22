package org.eclipse.ui.tests.dialogs;

import java.util.ResourceBundle;

import junit.framework.TestCase;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.tests.harness.util.DialogCheck;

public class TextMessageDialogsAuto extends TestCase {

    public TextMessageDialogsAuto(String name) {
        super(name);
    }

    private Shell getShell() {
        return DialogCheck.getShell();
    }

    private MessageDialog getConfirmDialog(String title, String message) {
        return new MessageDialog(getShell(), title, null, message,
                MessageDialog.QUESTION, new String[] {
                        IDialogConstants.OK_LABEL,
                        IDialogConstants.CANCEL_LABEL }, 0);
    }

    private MessageDialog getQuestionDialog(String title, String message) {
        return new MessageDialog(getShell(), title, null, message,
                MessageDialog.QUESTION,
                new String[] { IDialogConstants.YES_LABEL,
                        IDialogConstants.NO_LABEL }, 0);
    }

    public void testCloseFileDeleted() {
        Dialog dialog = null;
        ResourceBundle bundle = ResourceBundle
                .getBundle("org.eclipse.ui.texteditor.EditorMessages");
        if (bundle != null) {
            dialog = getConfirmDialog(
                    bundle
                            .getString("Editor_error_activated_deleted_close_title"),
                    bundle
                            .getString("Editor_error_activated_deleted_close_message"));
        }
        DialogCheck.assertDialogTexts(dialog, this);
    }
 
    public void testErrorClosing() {
        Dialog dialog = getQuestionDialog(WorkbenchMessages.Error,
                WorkbenchMessages.ErrorClosingNoArg);
        DialogCheck.assertDialogTexts(dialog, this);
    }

    public void testFileChanged() {
        MessageDialog dialog = null;
        ResourceBundle bundle = ResourceBundle
                .getBundle("org.eclipse.ui.texteditor.EditorMessages");
        if (bundle != null) {
            dialog = getQuestionDialog(
                    bundle.getString("Editor_error_activated_outofsync_title"),
                    bundle
                            .getString("Editor_error_activated_outofsync_message"));
        }
        DialogCheck.assertDialogTexts(dialog, this);
    }
    public void testSaveFileDeleted() {
        MessageDialog dialog = null;
        ResourceBundle bundle = ResourceBundle
                .getBundle("org.eclipse.ui.texteditor.EditorMessages");
        if (bundle != null) {
            dialog = new MessageDialog(
                    getShell(),
                    bundle
                            .getString("Editor_error_activated_deleted_save_title"),
                    null,
                    bundle
                            .getString("Editor_error_activated_deleted_save_message"),
                    MessageDialog.QUESTION,
                    new String[] {
                            bundle
                                    .getString("Editor_error_activated_deleted_save_button_save"),
                            bundle
                                    .getString("Editor_error_activated_deleted_save_button_close") },
                    0);
        }
        DialogCheck.assertDialogTexts(dialog, this);
    }

    public void testUpdateConflict() {
        MessageDialog dialog = null;
        ResourceBundle bundle = ResourceBundle
                .getBundle("org.eclipse.ui.texteditor.EditorMessages");
        if (bundle != null) {
            dialog = getQuestionDialog(bundle
                    .getString("Editor_error_save_outofsync_title"), bundle
                    .getString("Editor_error_save_outofsync_message"));
        }
        DialogCheck.assertDialogTexts(dialog, this);
    }

}

