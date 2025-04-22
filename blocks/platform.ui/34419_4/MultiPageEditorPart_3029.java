package org.eclipse.ui.part;

import org.eclipse.ui.IEditorPart;

public abstract class MultiPageEditorActionBarContributor extends
        EditorActionBarContributor {
    protected MultiPageEditorActionBarContributor() {
        super();
    }

    @Override
	public void setActiveEditor(IEditorPart part) {
        IEditorPart activeNestedEditor = null;
        if (part instanceof MultiPageEditorPart) {
            activeNestedEditor = ((MultiPageEditorPart) part).getActiveEditor();
        }
        setActivePage(activeNestedEditor);
    }

    public abstract void setActivePage(IEditorPart activeEditor);
}
