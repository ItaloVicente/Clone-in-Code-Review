package org.eclipse.ui.tests.internal.util;

import java.util.ArrayList;

public class FocusTestPass implements IDialogTestPass {
    private static final int CHECKLIST_SIZE = 1;

    public String title() {
        return "Test Pass: Initial Focus";
    }

    public String description() {
        return "Verify the initial focus of the dialogs.";
    }

    public String label() {
        return "&Initial Focus";
    }

    public ArrayList checkListTexts() {
        ArrayList list = new ArrayList(CHECKLIST_SIZE);
        list.add("&1) the initial focus is appropriate.");
        return list;
    }

    public String[] failureTexts() {
        String[] failureText = new String[CHECKLIST_SIZE];
        failureText[0] = "The initial focus is inappropriate.";
        return failureText;
    }

    public String queryText() {
        return "Is the initial focus of the dialog correct?";
    }

    public int getID() {
        return VerifyDialog.TEST_FOCUS;
    }
}
