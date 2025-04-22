package org.eclipse.ui.internal.part;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPart;

public interface IPartPropertyProvider {
    public void addPropertyListener(IWorkbenchPart part, IPropertyListener l);
    public void removePropertyListener(IWorkbenchPart part, IPropertyListener l);
    public String getTitleToolTip();
    public Image getTitleImage();
    public String getPartName();
    public String getTitle();
    public String getContentDescription();
    public IEditorInput getEditorInput();
    public boolean isDirty();
}
