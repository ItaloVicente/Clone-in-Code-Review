package org.eclipse.ui.internal.quickaccess;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.quickaccess.IQuickAccessElement;
import org.eclipse.ui.quickaccess.IQuickAccessProvider;

public class ExtensionQuickAccessProvider extends QuickAccessProvider {

	static final String ATTRIBUTE_ID = "id"; //$NON-NLS-1$
	private static final String ATTRIBUTE_NAME = "name"; //$NON-NLS-1$
	private static final String ATTRIBUTE_CLASS = "class"; //$NON-NLS-1$
	private static final String ATTRIBUTE_ALWAYS_PRESENT = "alwaysPresent"; //$NON-NLS-1$
	private static final String ATTRIBUTE_IMAGE = "image"; //$NON-NLS-1$

	private IConfigurationElement configurationElement;
	private IEclipseContext context;
	private IQuickAccessProvider provider;
	private IQuickAccessElement[] rawElements;
	private QuickAccessElement[] wrappedElements;
	private Map<String, QuickAccessElement> idToElement;
	private String id;

	public ExtensionQuickAccessProvider(IConfigurationElement element, IEclipseContext context) {
		this.configurationElement = element;
		this.context = context;

		String namespace = configurationElement.getNamespaceIdentifier();
		this.id = namespace + "." + configurationElement.getAttribute(ATTRIBUTE_ID); //$NON-NLS-1$
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return configurationElement.getAttribute(ATTRIBUTE_NAME);
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		String path = configurationElement.getAttribute(ATTRIBUTE_IMAGE);
		ImageDescriptor descriptor = AbstractUIPlugin.imageDescriptorFromPlugin(
				configurationElement.getNamespaceIdentifier(), path);
		return descriptor;
	}

	@Override
	public QuickAccessElement[] getElements() {
		IQuickAccessElement[] elements = getProvider().getElements();
		if (elements != rawElements) {
			rawElements = elements;
			wrappedElements = new QuickAccessElement[elements.length];
			idToElement = new HashMap<>();
			if (elements != null)
				for (int index = 0; index < elements.length; index++) {
					QuickAccessElement wrapped = new ExtensionQuickAccessElement(this, elements[index]);
					idToElement.put(wrapped.getId(), wrapped);
					wrappedElements[index] = wrapped;
				}
		}
		return wrappedElements;
	}

	@Override
	public QuickAccessElement getElementForId(String id) {
		getElements();
		return idToElement.get(id);
	}

	@Override
	public boolean isAlwaysPresent() {
		String value = configurationElement.getAttribute(ATTRIBUTE_ALWAYS_PRESENT);
		return value == null ? false : Boolean.valueOf(value);
	}

	public IQuickAccessProvider getProvider() {
		if (provider != null)
			return provider;
		synchronized (this) {
			if (provider != null)
				return provider;
			try {
				Object object = configurationElement.createExecutableExtension(ATTRIBUTE_CLASS);
				if (object instanceof IQuickAccessProvider) {
					provider = (IQuickAccessProvider) object;
					provider.setContext(context);
				}
			} catch (CoreException e) {
				WorkbenchPlugin.log(
						"Unable to Quick Access extension from: " + configurationElement.getNamespaceIdentifier(), e);//$NON-NLS-1$
				provider = new NullQuickAccessProvider();
			}
		}
		return provider;
	}

	@Override
	protected void doReset() {
		wrappedElements = null;
		idToElement = null;
		getProvider().reset();
	}
}

class NullQuickAccessProvider implements IQuickAccessProvider {

	@Override
	public IQuickAccessElement[] getElements() {
		return new IQuickAccessElement[0];
	}

	@Override
	public IQuickAccessElement getElementForId(String id) {
		return null;
	}

	@Override
	public void reset() {

	}

	@Override
	public void setContext(IEclipseContext context) {

	}

}
