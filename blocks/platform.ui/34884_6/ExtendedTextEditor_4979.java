package org.eclipse.ui.tests.internal;

import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

public class ExtendedSourceViewer extends SourceViewer {

    public ExtendedSourceViewer(Composite parent, IVerticalRuler ruler,
            int styles) {
        super(parent, ruler, styles);
    }

    @Override
	public ISelection getSelection() {
        Point p = getSelectedRange();
        if (p.x == -1 || p.y == -1)
            return TextSelection.emptySelection();

        return new ExtendedTextSelection(getDocument(), p.x, p.y);
    }

    @Override
	protected void selectionChanged(int offset, int length) {
        ISelection selection = new ExtendedTextSelection(getDocument(), offset,
                length);
        SelectionChangedEvent event = new SelectionChangedEvent(this, selection);
        fireSelectionChanged(event);
    }
}

