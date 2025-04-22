package org.eclipse.ui.tests.internal.util;

import java.util.ArrayList;

public interface IDialogTestPass {
    public String title();

    public String description();

    public String label();

    public ArrayList checkListTexts();

    public String[] failureTexts();

    public String queryText();

    public int getID();
}

