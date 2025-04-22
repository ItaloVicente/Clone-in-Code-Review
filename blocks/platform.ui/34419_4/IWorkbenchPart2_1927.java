package org.eclipse.ui;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public interface IWorkbenchPart extends IAdaptable {

    public static final int PROP_TITLE = IWorkbenchPartConstants.PROP_TITLE;

    public void addPropertyListener(IPropertyListener listener);

    public void createPartControl(Composite parent);

    public void dispose();

    public IWorkbenchPartSite getSite();

    public String getTitle();

    public Image getTitleImage();

    public String getTitleToolTip();

    public void removePropertyListener(IPropertyListener listener);

    public void setFocus();
}
