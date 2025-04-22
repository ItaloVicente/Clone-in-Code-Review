package org.eclipse.ui.internal.registry;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.ui.internal.WorkbenchPlugin;

public abstract class RegistryReader {
	
    protected RegistryReader() {
    }

    protected static void logError(IConfigurationElement element, String text) {
        IExtension extension = element.getDeclaringExtension();
        StringBuffer buf = new StringBuffer();
        buf
                .append("Plugin " + extension.getNamespace() + ", extension " + extension.getExtensionPointUniqueIdentifier());//$NON-NLS-2$//$NON-NLS-1$
        String id = element.getAttribute("id"); //$NON-NLS-1$
        if (id != null) {
        	buf.append(", id "); //$NON-NLS-1$
        	buf.append(id);
        }
        buf.append(": " + text);//$NON-NLS-1$
        WorkbenchPlugin.log(buf.toString());
    }

    protected static void logMissingAttribute(IConfigurationElement element,
            String attributeName) {
        logError(element,
                "Required attribute '" + attributeName + "' not defined");//$NON-NLS-2$//$NON-NLS-1$
    }

    protected static void logMissingElement(IConfigurationElement element,
            String elementName) {
        logError(element,
                "Required sub element '" + elementName + "' not defined");//$NON-NLS-2$//$NON-NLS-1$
    }

    protected static void logUnknownElement(IConfigurationElement element) {
        logError(element, "Unknown extension tag found: " + element.getName());//$NON-NLS-1$
    }

    public static IExtension[] orderExtensions(IExtension[] extensions) {
        IExtension[] sortedExtension = new IExtension[extensions.length];
        System.arraycopy(extensions, 0, sortedExtension, 0, extensions.length);
        Comparator comparer = new Comparator() {
            @Override
			public int compare(Object arg0, Object arg1) {
                String s1 = ((IExtension) arg0).getNamespace();
                String s2 = ((IExtension) arg1).getNamespace();
                return s1.compareToIgnoreCase(s2);
            }
        };
        Collections.sort(Arrays.asList(sortedExtension), comparer);
        return sortedExtension;
    }

    protected abstract boolean readElement(IConfigurationElement element);

    protected void readElementChildren(IConfigurationElement element) {
        readElements(element.getChildren());
    }

    protected void readElements(IConfigurationElement[] elements) {
        for (int i = 0; i < elements.length; i++) {
            if (!readElement(elements[i])) {
				logUnknownElement(elements[i]);
			}
        }
    }

    protected void readExtension(IExtension extension) {
        readElements(extension.getConfigurationElements());
    }

    public void readRegistry(IExtensionRegistry registry, String pluginId,
            String extensionPoint) {
        IExtensionPoint point = registry.getExtensionPoint(pluginId,
                extensionPoint);
        if (point == null) {
			return;
		}
        IExtension[] extensions = point.getExtensions();
        extensions = orderExtensions(extensions);
        for (int i = 0; i < extensions.length; i++) {
			readExtension(extensions[i]);
		}
    }
    
    public static String getDescription(IConfigurationElement configElement) {
		IConfigurationElement[] children = configElement.getChildren(IWorkbenchRegistryConstants.TAG_DESCRIPTION);
	    if (children.length >= 1) {
	        return children[0].getValue();
	    }
	    return "";//$NON-NLS-1$
    }
    
    public static String getClassValue(IConfigurationElement configElement, String classAttributeName) {
    	String className = configElement.getAttribute(classAttributeName);
    	if (className != null) {
			return className;
		}
		IConfigurationElement [] candidateChildren = configElement.getChildren(classAttributeName);
		if (candidateChildren.length == 0) {
			return null;
		}
	
		return candidateChildren[0].getAttribute(IWorkbenchRegistryConstants.ATT_CLASS);
    }
}
