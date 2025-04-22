package org.eclipse.ui.tests.internal;

import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.editors.text.TextEditor;

public class ExtendedTextEditor extends TextEditor {

    public ExtendedTextEditor() {
        super();
    }

    @Override
	protected ISourceViewer createSourceViewer(Composite parent,
            IVerticalRuler ruler, int styles) {
        return new ExtendedSourceViewer(parent, ruler, styles);
    }

    @Override
	public boolean isDirty() {
        return false;
    }

    public void setText(String text) {
        ExtendedSourceViewer viewer = (ExtendedSourceViewer) getSourceViewer();
        StyledText widget = viewer.getTextWidget();
        widget.setText(text);
        viewer.setSelectedRange(0, text.length());
    }

}

