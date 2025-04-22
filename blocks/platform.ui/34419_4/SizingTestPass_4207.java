package org.eclipse.ui.tests.internal.util;

import java.util.List;

public interface IDialogTestPass {
    public String title();

    public String description();

    public String label();

	public List<String> checkListTexts();

    public String[] failureTexts();

    public String queryText();

    public int getID();
}

