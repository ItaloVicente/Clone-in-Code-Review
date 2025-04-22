package org.eclipse.ui.part;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPartConstants;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.internal.util.Util;

public abstract class ViewPart extends WorkbenchPart implements IViewPart {

    private IPropertyListener compatibilityTitleListener = new IPropertyListener() {
        @Override
		public void propertyChanged(Object source, int propId) {
            if (propId == IWorkbenchPartConstants.PROP_TITLE) {
                setDefaultContentDescription();
            }
        }
    };

    protected ViewPart() {
        super();

        addPropertyListener(compatibilityTitleListener);
    }

    @Override
	public IViewSite getViewSite() {
        return (IViewSite) getSite();
    }

    
    @Override
	public void init(IViewSite site) throws PartInitException {
        setSite(site);

        setDefaultContentDescription();
    }

    @Override
	public void init(IViewSite site, IMemento memento) throws PartInitException {
        init(site);
    }

  
    @Override
	public void saveState(IMemento memento) {
    }

    @Override
	protected void setPartName(String partName) {
        if (compatibilityTitleListener != null) {
            removePropertyListener(compatibilityTitleListener);
            compatibilityTitleListener = null;
        }

        super.setPartName(partName);
    }

    @Override
	protected void setContentDescription(String description) {
        if (compatibilityTitleListener != null) {
            removePropertyListener(compatibilityTitleListener);
            compatibilityTitleListener = null;
        }

        super.setContentDescription(description);
    }

    @Override
	public void setInitializationData(IConfigurationElement cfig,
            String propertyName, Object data) {
        super.setInitializationData(cfig, propertyName, data);

        setDefaultContentDescription();
    }

    private void setDefaultContentDescription() {
        if (compatibilityTitleListener == null) {
            return;
        }

        String partName = getPartName();
        String title = getTitle();

        if (Util.equals(partName, title)) {
            internalSetContentDescription(""); //$NON-NLS-1$
        } else {
            internalSetContentDescription(title);
        }
    }

    @Override
	protected final void checkSite(IWorkbenchPartSite site) {
        super.checkSite(site);
        Assert.isTrue(site instanceof IViewSite, "The site for a view must be an IViewSite"); //$NON-NLS-1$
    }    
}
