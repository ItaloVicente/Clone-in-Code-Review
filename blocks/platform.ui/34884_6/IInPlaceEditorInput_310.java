package org.eclipse.ui;

public interface IInPlaceEditor extends IEditorPart {
    public void sourceDeleted();

    public void sourceChanged(IInPlaceEditorInput input);
}
