package org.eclipse.ui.part;

import com.ibm.icu.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.commands.common.EventManager;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.workbench.renderers.swt.ContributedPartRenderer;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPart3;
import org.eclipse.ui.IWorkbenchPartConstants;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.PartSite;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public abstract class WorkbenchPart extends EventManager implements
		IWorkbenchPart3, IExecutableExtension, IWorkbenchPartOrientation {
    private String title = ""; //$NON-NLS-1$

    private ImageDescriptor imageDescriptor;

    private Image titleImage;

	private String toolTip = ""; //$NON-NLS-1$

    private IConfigurationElement configElement;

    private IWorkbenchPartSite partSite;

    private String partName = ""; //$NON-NLS-1$

    private String contentDescription = ""; //$NON-NLS-1$
    
    private ListenerList partChangeListeners = new ListenerList();

    protected WorkbenchPart() {
        super();
    }

    @Override
	public void addPropertyListener(IPropertyListener l) {
        addListenerObject(l);
    }

    @Override
	public abstract void createPartControl(Composite parent);

    @Override
	public void dispose() {
        if (imageDescriptor != null) {
            JFaceResources.getResources().destroyImage(imageDescriptor);
        }

        clearListeners();
        partChangeListeners.clear();
    }

    protected void firePropertyChange(final int propertyId) {
        Object[] array = getListeners();
        for (int nX = 0; nX < array.length; nX++) {
            final IPropertyListener l = (IPropertyListener) array[nX];
            try {
                l.propertyChanged(WorkbenchPart.this, propertyId);
            } catch (RuntimeException e) {
                WorkbenchPlugin.log(e);
            }
        }
    }

    @Override
	public Object getAdapter(Class adapter) {


        return Platform.getAdapterManager().getAdapter(this, adapter);
    }

    protected IConfigurationElement getConfigurationElement() {
        return configElement;
    }

    protected Image getDefaultImage() {
        return PlatformUI.getWorkbench().getSharedImages().getImage(
                ISharedImages.IMG_DEF_VIEW);
    }

    @Override
	public IWorkbenchPartSite getSite() {
        return partSite;
    }

    @Override
	public String getTitle() {
        return title;
    }

    @Override
	public Image getTitleImage() {
        if (titleImage != null) {
            return titleImage;
        }
        return getDefaultImage();
    }

    @Override
	public String getTitleToolTip() {
        return toolTip;
    }

    @Override
	public void removePropertyListener(IPropertyListener l) {
        removeListenerObject(l);
    }

    @Override
	public abstract void setFocus();

    @Override
	public void setInitializationData(IConfigurationElement cfig,
            String propertyName, Object data) {

        configElement = cfig;

        partName = Util.safeString(cfig.getAttribute("name"));//$NON-NLS-1$;
        title = partName;

        String strIcon = cfig.getAttribute("icon");//$NON-NLS-1$
        if (strIcon == null) {
			return;
		}

        imageDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(
                configElement.getNamespace(), strIcon);

        if (imageDescriptor == null) {
			return;
		}

        titleImage = JFaceResources.getResources().createImageWithDefault(imageDescriptor);
    }

    protected void setSite(IWorkbenchPartSite site) {
        checkSite(site);
        this.partSite = site;
    }

    protected void checkSite(IWorkbenchPartSite site) {
    }

    @Deprecated
	protected void setTitle(String title) {
        title = Util.safeString(title);

        if (Util.equals(this.title, title)) {
			return;
		}
        this.title = title;
        firePropertyChange(IWorkbenchPart.PROP_TITLE);
    }

    protected void setTitleImage(Image titleImage) {
        Assert.isTrue(titleImage == null || !titleImage.isDisposed());
        if (this.titleImage == titleImage) {
			return;
		}
        this.titleImage = titleImage;
        firePropertyChange(IWorkbenchPart.PROP_TITLE);
        if (imageDescriptor != null) {
            JFaceResources.getResources().destroyImage(imageDescriptor);
            imageDescriptor = null;
        }
    }

    protected void setTitleToolTip(String toolTip) {
        toolTip = Util.safeString(toolTip);
        if (Util.equals(this.toolTip, toolTip)) {
			return;
		}
        this.toolTip = toolTip;
        firePropertyChange(IWorkbenchPart.PROP_TITLE);
    }

    public void showBusy(boolean busy) {
    }

    @Override
	public String getPartName() {
        return partName;
    }

    protected void setPartName(String partName) {

        internalSetPartName(partName);

        setDefaultTitle();
    }

    void setDefaultTitle() {
        String description = getContentDescription();
        String name = getPartName();
        String newTitle = name;

        if (!Util.equals(description, "")) { //$NON-NLS-1$
            newTitle = MessageFormat
                    .format(
                            WorkbenchMessages.WorkbenchPart_AutoTitleFormat, name, description);
        }

        setTitle(newTitle);
    }

    @Override
	public String getContentDescription() {
        return contentDescription;
    }

    protected void setContentDescription(String description) {
        internalSetContentDescription(description);

        setDefaultTitle();
    }

    void internalSetContentDescription(String description) {
        Assert.isNotNull(description);

        if (Util.equals(contentDescription, description)) {
			return;
		}
        this.contentDescription = description;

		if (partSite instanceof PartSite) {
			PartSite site = (PartSite) partSite;
			ContributedPartRenderer.setDescription(site.getModel(), description);
		}
        firePropertyChange(IWorkbenchPartConstants.PROP_CONTENT_DESCRIPTION);
    }

    void internalSetPartName(String partName) {
        partName = Util.safeString(partName);

        Assert.isNotNull(partName);

        if (Util.equals(this.partName, partName)) {
			return;
		}
        this.partName = partName;

        firePropertyChange(IWorkbenchPartConstants.PROP_PART_NAME);

    }
    
    @Override
	public int getOrientation(){
    	return Window.getDefaultOrientation();
    }

    @Override
	public void addPartPropertyListener(IPropertyChangeListener listener) {
    	partChangeListeners.add(listener);
    }
    
    @Override
	public void removePartPropertyListener(IPropertyChangeListener listener) {
    	partChangeListeners.remove(listener);
    }
    
    protected void firePartPropertyChanged(String key, String oldValue, String newValue) {
    	final PropertyChangeEvent event = new PropertyChangeEvent(this, key, oldValue, newValue);
    	Object[] l = partChangeListeners.getListeners();
    	for (int i = 0; i < l.length; i++) {
			try {
				((IPropertyChangeListener)l[i]).propertyChange(event);
			} catch (RuntimeException e) {
				WorkbenchPlugin.log(e);
			}
		}
    }
    
	private Map<String, String> partProperties = new HashMap<String, String>();
    
    @Override
	public void setPartProperty(String key, String value) {
		String oldValue = partProperties.get(key);
		if (value == null) {
    		partProperties.remove(key);
    	} else {
    		partProperties.put(key, value);
    	}
    	firePartPropertyChanged(key, oldValue, value);
    }
    
    @Override
	public String getPartProperty(String key) {
		return partProperties.get(key);
    }
    
    @Override
	public Map<String, String> getPartProperties() {
    	return Collections.unmodifiableMap(partProperties);
    }
}
