
package org.eclipse.ui;

public interface IEditorReference extends IWorkbenchPartReference {
    public String getFactoryId();

    public String getName();

    public IEditorPart getEditor(boolean restore);

    public boolean isPinned();

    public IEditorInput getEditorInput() throws PartInitException;
}
