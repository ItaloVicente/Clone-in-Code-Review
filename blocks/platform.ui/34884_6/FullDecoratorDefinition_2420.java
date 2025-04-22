package org.eclipse.ui.internal.decorators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.registry.RegistryReader;

public class DecoratorRegistryReader extends RegistryReader {

    private Collection values = new ArrayList();

    private Collection ids = new HashSet();

    public DecoratorRegistryReader() {
        super();
    }

    @Override
	public boolean readElement(IConfigurationElement element) {

    	DecoratorDefinition desc = getDecoratorDefinition(element);
    	
    	if (desc == null) {
			return false;
		}
    	
        values.add(desc);

        return true;

    }
    
    DecoratorDefinition getDecoratorDefinition(IConfigurationElement element){

        String id = element.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
        if (ids.contains(id)) {
            logDuplicateId(element);
            return null;
        }
        ids.add(id);

        boolean noClass = element.getAttribute(DecoratorDefinition.ATT_CLASS) == null;

        if (Boolean.valueOf(element.getAttribute(IWorkbenchRegistryConstants.ATT_LIGHTWEIGHT)).booleanValue() || noClass) {

            String iconPath = element.getAttribute(LightweightDecoratorDefinition.ATT_ICON);

            if (noClass && iconPath == null) {
                logMissingElement(element, LightweightDecoratorDefinition.ATT_ICON);
                return null;
            }

            return new LightweightDecoratorDefinition(id, element);
        } 
        return new FullDecoratorDefinition(id, element);
        
    }

    Collection readRegistry(IExtensionRegistry in) {
        values.clear();
        ids.clear();
        readRegistry(in, PlatformUI.PLUGIN_ID,
                IWorkbenchRegistryConstants.PL_DECORATORS);
        return values;
    }

    public Collection getValues() {
        return values;
    }

    protected void logDuplicateId(IConfigurationElement element) {
        logError(element, "Duplicate id found: " + element.getAttribute(IWorkbenchRegistryConstants.ATT_ID));//$NON-NLS-1$
    }

}
