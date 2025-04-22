package org.eclipse.ui.intro;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

public interface IIntroPart extends IAdaptable {

	public static final int PROP_TITLE = IWorkbenchPart.PROP_TITLE;

    IIntroSite getIntroSite();

    public void init(IIntroSite site, IMemento memento)
            throws PartInitException;

    public void standbyStateChanged(boolean standby);

    public void saveState(IMemento memento);

    public void addPropertyListener(IPropertyListener listener);

    public void createPartControl(Composite parent);

    public void dispose();

    public Image getTitleImage();
    
    public String getTitle();

    public void removePropertyListener(IPropertyListener listener);

    public void setFocus();
}
