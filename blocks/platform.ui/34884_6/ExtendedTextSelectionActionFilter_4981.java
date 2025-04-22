package org.eclipse.ui.tests.internal;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.ui.IActionFilter;

public class ExtendedTextSelection extends TextSelection implements IAdaptable {
    static private ExtendedTextSelectionActionFilter filter = new ExtendedTextSelectionActionFilter();

    public ExtendedTextSelection(int offset, int length) {
        super(offset, length);
    }

    public ExtendedTextSelection(IDocument document, int offset, int length) {
        super(document, offset, length);
    }

    @Override
	public Object getAdapter(Class adapter) {
        if (adapter == IActionFilter.class) {
            return filter;
        }
        return null;
    }

}

