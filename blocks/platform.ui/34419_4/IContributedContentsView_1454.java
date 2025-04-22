package org.eclipse.ui.part;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPartConstants;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;

public abstract class EditorPart extends WorkbenchPart implements IEditorPart {

    private IEditorInput editorInput = null;

    private IPropertyListener compatibilityTitleListener = new IPropertyListener() {
        @Override
		public void propertyChanged(Object source, int propId) {
            if (propId == IWorkbenchPartConstants.PROP_TITLE) {
                setDefaultPartName();
            }
        }
    };

    protected EditorPart() {
        super();

        addPropertyListener(compatibilityTitleListener);
    }

    @Override
	public abstract void doSave(IProgressMonitor monitor);

    @Override
	public abstract void doSaveAs();

    @Override
	public IEditorInput getEditorInput() {
        return editorInput;
    }

    @Override
	public IEditorSite getEditorSite() {
        return (IEditorSite) getSite();
    }

    @Override
	public String getTitleToolTip() {
        if (editorInput == null) {
			return super.getTitleToolTip();
		}
		return editorInput.getToolTipText();
    }

    @Override
	public abstract void init(IEditorSite site, IEditorInput input)
            throws PartInitException;

    @Override
	public abstract boolean isDirty();

    @Override
	public abstract boolean isSaveAsAllowed();

    @Override
	public boolean isSaveOnCloseNeeded() {
        return isDirty();
    }

    protected void setInput(IEditorInput input) {
    	Assert.isLegal(input != null);
        editorInput = input;
    }
    
    protected void setInputWithNotify(IEditorInput input) {
		Assert.isLegal(input != null);
        editorInput = input;
        firePropertyChange(PROP_INPUT);
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
	protected void setPartName(String partName) {
        if (compatibilityTitleListener != null) {
            removePropertyListener(compatibilityTitleListener);
            compatibilityTitleListener = null;
        }

        super.setPartName(partName);
    }

    @Override
	public void setInitializationData(IConfigurationElement cfig,
            String propertyName, Object data) {
        super.setInitializationData(cfig, propertyName, data);

        setDefaultPartName();
    }
    

    private void setDefaultPartName() {
        if (compatibilityTitleListener == null) {
            return;
        }

        internalSetPartName(getTitle());
    }

    @Override
	void setDefaultTitle() {
        setTitle(getPartName());
    }

    @Override
	protected final void checkSite(IWorkbenchPartSite site) {
        super.checkSite(site);
        Assert.isTrue(site instanceof IEditorSite, "The site for an editor must be an IEditorSite"); //$NON-NLS-1$
    }    

}
