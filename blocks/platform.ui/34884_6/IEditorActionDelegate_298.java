package org.eclipse.ui;

public interface IEditorActionBarContributor {
    public void init(IActionBars bars, IWorkbenchPage page);

    public void setActiveEditor(IEditorPart targetEditor);

    public void dispose();
}
