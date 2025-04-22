package org.eclipse.ui.tests.internal.util;

import java.util.ArrayList;

class SizingTestPass implements IDialogTestPass {
    private static final int CHECKLIST_SIZE = 5;

    public String title() {
        return "Test Pass: Sizing and Display";
    }

    public String description() {
        return "Verify the sizing and display of the dialogs and widgets.";
    }

    public String label() {
        return "&Sizing and Display";
    }

    public ArrayList checkListTexts() {
        ArrayList list = new ArrayList(CHECKLIST_SIZE);
        list.add("&1) the correct dialog displays.");
        list
                .add("&2) the dialog is an appropriate size for the required resolution (1024x768).");
        list.add("&3) the texts are correct and not cut off.");
        list.add("&4) all strings have been externalized properly.");
        list.add("&5) all the widgets are viewable and not cut off.");
        return list;
    }

    public String[] failureTexts() {
        String[] failureText = new String[CHECKLIST_SIZE];
        failureText[0] = "The wrong dialog displayed.";
        failureText[1] = "The dialog is too large for the required resolution.";
        failureText[2] = "Text labels are wrong or cut off.";
        failureText[3] = "Some strings have not been externalized properly.";
        failureText[4] = "Some widgets are cut off.";
        return failureText;
    }

    public String queryText() {
        return "Is the sizing and display of the dialog correct?";
    }

    public int getID() {
        return VerifyDialog.TEST_SIZING;
    }
}
