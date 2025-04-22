package org.eclipse.ui.part;

import org.eclipse.core.commands.common.EventManager;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.intro.IntroMessages;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.intro.IIntroPart;
import org.eclipse.ui.intro.IIntroSite;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public abstract class IntroPart extends EventManager implements IIntroPart,
		IExecutableExtension {

    private IConfigurationElement configElement;

    private ImageDescriptor imageDescriptor;

    private IIntroSite partSite;

    private Image titleImage;

	private String titleLabel;

    protected IntroPart() {
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
        if (titleImage != null) {
            JFaceResources.getResources().destroyImage(imageDescriptor);
            titleImage = null;
        }

        clearListeners();
    }

    protected void firePropertyChange(final int propertyId) {
        Object[] array = getListeners();
        for (int nX = 0; nX < array.length; nX++) {
            final IPropertyListener l = (IPropertyListener) array[nX];
            SafeRunner.run(new SafeRunnable() {

                @Override
				public void run() {
                    l.propertyChanged(this, propertyId);
                }
            });
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
	public final IIntroSite getIntroSite() {
        return partSite;
    }

    @Override
	public Image getTitleImage() {
        if (titleImage != null) {
            return titleImage;
        }
        return getDefaultImage();
    }
    
    @Override
	public String getTitle() {
    	if (titleLabel != null) {
    		return titleLabel;
    	}
    	return getDefaultTitle();
    }

	private String getDefaultTitle() {
		return IntroMessages.Intro_default_title;
	}

    @Override
	public void init(IIntroSite site, IMemento memento)
            throws PartInitException {
        setSite(site);
    }

    protected void setSite(IIntroSite site) {
        this.partSite = site;
    }

    @Override
	public void removePropertyListener(IPropertyListener l) {
        removeListenerObject(l);
    }

    @Override
	public void saveState(IMemento memento) {
    }

    @Override
	public abstract void setFocus();

    @Override
	public void setInitializationData(IConfigurationElement cfig,
            String propertyName, Object data) {

        configElement = cfig;

        titleLabel = cfig.getAttribute(IWorkbenchRegistryConstants.ATT_LABEL);
        
        String strIcon = cfig.getAttribute(IWorkbenchRegistryConstants.ATT_ICON);
        if (strIcon == null) {
			return;
		}

        imageDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(
                configElement.getNamespace(), strIcon);

        if (imageDescriptor == null) {
			return;
		}

        Image image = JFaceResources.getResources().createImageWithDefault(imageDescriptor);
        titleImage = image;
    }

    protected void setTitleImage(Image titleImage) {
        Assert.isTrue(titleImage == null || !titleImage.isDisposed());
        if (this.titleImage == titleImage) {
			return;
		}
        this.titleImage = titleImage;
        firePropertyChange(IIntroPart.PROP_TITLE);
    }
    
    protected void setTitle(String titleLabel) {
    	Assert.isNotNull(titleLabel);
    	if (Util.equals(this.titleLabel, titleLabel))
    		return;
    	this.titleLabel = titleLabel;
    	firePropertyChange(IIntroPart.PROP_TITLE);
    }
}
