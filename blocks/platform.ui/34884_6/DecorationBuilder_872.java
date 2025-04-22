package org.eclipse.ui.internal.decorators;

import java.net.URL;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.ui.internal.util.BundleUtility;

public class DeclarativeDecorator implements ILightweightLabelDecorator {
 
	private String iconLocation;

    private IConfigurationElement configElement;

    private ImageDescriptor descriptor;

    DeclarativeDecorator(IConfigurationElement definingElement, String iconPath) {
        this.iconLocation = iconPath;
        this.configElement = definingElement;
    }

    @Override
	public void addListener(ILabelProviderListener listener) {
    }

    @Override
	public void dispose() {
    }

    @Override
	public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    @Override
	public void removeListener(ILabelProviderListener listener) {
    }

    @Override
	public void decorate(Object element, IDecoration decoration) {
        if (descriptor == null) {
            URL url = BundleUtility.find(configElement.getDeclaringExtension()
                    .getNamespace(), iconLocation);
            if (url == null) {
				return;
			}
            descriptor = ImageDescriptor.createFromURL(url);
        }
        decoration.addOverlay(descriptor);
    }
}
