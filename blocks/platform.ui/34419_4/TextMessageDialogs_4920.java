package org.eclipse.ui.tests.dialogs;


public class TextEditorTestStub {
    private TextEditorTestStub() {
    }

     public static FindReplaceDialog newFindReplaceDialog(Shell parentShell) {
     return new FindReplaceDialog(parentShell);
     }
}

