
package org.eclipse.ui;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.graphics.Image;

public interface IWorkbenchPartReference {
    public IWorkbenchPart getPart(boolean restore);

    public String getTitle();

    public Image getTitleImage();

    public String getTitleToolTip();

    public String getId();

    public void addPropertyListener(IPropertyListener listener);

    public void removePropertyListener(IPropertyListener listener);

    public IWorkbenchPage getPage();

    public String getPartName();

    public String getContentDescription();
    

    public boolean isDirty();
    
    public String getPartProperty(String key);
    
    public void addPartPropertyListener(IPropertyChangeListener listener);
    
    public void removePartPropertyListener(IPropertyChangeListener listener);
}
