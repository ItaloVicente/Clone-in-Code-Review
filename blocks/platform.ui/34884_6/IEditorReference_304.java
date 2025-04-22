
package org.eclipse.ui;

public interface IEditorPart extends IWorkbenchPart, ISaveablePart {

    public static final int PROP_DIRTY = IWorkbenchPartConstants.PROP_DIRTY;

    public static final int PROP_INPUT = IWorkbenchPartConstants.PROP_INPUT;

    public IEditorInput getEditorInput();

    public IEditorSite getEditorSite();

    public void init(IEditorSite site, IEditorInput input)
            throws PartInitException;
}
