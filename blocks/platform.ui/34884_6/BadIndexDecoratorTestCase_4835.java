package org.eclipse.ui.tests.decorators;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.ui.tests.TestPlugin;

public class BadIndexDecorator implements ILightweightLabelDecorator {

     private Set listeners = new HashSet();

    private ImageDescriptor descriptor;
   

    @Override
	public void addListener(ILabelProviderListener listener) {
        listeners.add(listener);
    }

    @Override
	public void dispose() {
        listeners = new HashSet();
    }

    @Override
	public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    @Override
	public void removeListener(ILabelProviderListener listener) {
        listeners.remove(listener);
    }


    public void refreshListeners(Object element) {
        Iterator iterator = listeners.iterator();
        while (iterator.hasNext()) {
            LabelProviderChangedEvent event = new LabelProviderChangedEvent(
                    this, element);
            ((ILabelProviderListener) iterator.next())
                    .labelProviderChanged(event);
        }
    }

    public ImageDescriptor getOverlay(Object element) {
        Assert.isTrue(element instanceof IResource);
        if (descriptor == null) {
            URL source = TestPlugin.getDefault().getDescriptor()
                    .getInstallURL();
            try {
                descriptor = ImageDescriptor.createFromURL(new URL(source,
                        "icons/binary_co.gif"));
            } catch (MalformedURLException exception) {
                return null;
            }
        }
        return descriptor;

    }

    @Override
	public void decorate(Object element, IDecoration decoration) {
        decoration.addOverlay(getOverlay(element), 17);
    }

}
