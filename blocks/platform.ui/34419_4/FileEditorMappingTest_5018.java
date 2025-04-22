package org.eclipse.ui.tests.internal;

import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ui.IActionFilter;

public class ExtendedTextSelectionActionFilter implements IActionFilter {

    public static final String IS_EMPTY = "isEmpty"; //$NON-NLS-1$

    public static final String TEXT = "text"; //$NON-NLS-1$

    public static final String CASE_INSENSITIVE_TEXT = "caseInsensitiveText"; //$NON-NLS-1$

    @Override
	public boolean testAttribute(Object target, String name, String value) {
        ITextSelection sel = (ITextSelection) target;
        if (name.equals(IS_EMPTY)) {
            return (sel.getLength() == 0);
        } else if (name.equals(TEXT)) {
            String text = sel.getText();
            return (text.indexOf(value) >= 0);
        } else if (name.equals(CASE_INSENSITIVE_TEXT)) {
            String text = sel.getText().toLowerCase();
            value = value.toLowerCase();
            return (text.indexOf(value) >= 0);
        }
        return false;
    }

}

