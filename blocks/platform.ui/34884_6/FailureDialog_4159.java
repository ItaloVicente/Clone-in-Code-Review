package org.eclipse.ui.tests.internal.util;

import java.util.ArrayList;

public class AccessibilityTestPass implements IDialogTestPass {
    private static final int CHECKLIST_SIZE = 5;

    public String title() {
        return "Test Pass: Accessibility";
    }

    public String description() {
        return "Verify the accessibility of the dialogs.";
    }

    public String label() {
        return "&Accessibility";
    }

    public ArrayList checkListTexts() {
        ArrayList list = new ArrayList(CHECKLIST_SIZE);
        list.add("&1) all widgets are accessible by tabbing.");
        list.add("&2) forwards and backwards tabbing is in a logical order");
        list
                .add("&3) all the widgets with labels have an appropriate mnemonic.");
        list.add("&4) there are no duplicate mnemonics.");
        list.add("&5) selectable widgets can be selected using the spacebar.");
        return list;
    }

    public String[] failureTexts() {
        String[] failureText = new String[CHECKLIST_SIZE];
        failureText[0] = "Some widgets aren't accessible by tabbing.";
        failureText[1] = "Tabbing order is illogical.";
        failureText[2] = "Missing or inappropriate mnemonics.";
        failureText[3] = "Duplicate mnemonics.";
        failureText[4] = "Some widgets cannot be selected using the spacebar.";
        return failureText;
    }

    public String queryText() {
        return "Is the accessibility of the dialog acceptable?";
    }

    public int getID() {
        return VerifyDialog.TEST_ACCESS;
    }
}
