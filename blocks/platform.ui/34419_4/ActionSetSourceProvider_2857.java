package org.eclipse.ui.internal.registry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPlugin;

public class WorkingSetRegistryReader extends RegistryReader {
    

    private WorkingSetRegistry registry;

    public WorkingSetRegistryReader() {
        super();
    }

    public WorkingSetRegistryReader(WorkingSetRegistry registry) {
        super();
        this.registry = registry;
    }

    @Override
	public boolean readElement(IConfigurationElement element) {
        if (element.getName().equals(IWorkbenchRegistryConstants.TAG_WORKING_SET)) {
            try {
                WorkingSetDescriptor desc = new WorkingSetDescriptor(element);
                registry.addWorkingSetDescriptor(desc);
            } catch (CoreException e) {
                WorkbenchPlugin
                        .log(
                                "Unable to create working set descriptor.", e.getStatus());//$NON-NLS-1$
            }
            return true;
        }

        return false;
    }

    public void readWorkingSets(IExtensionRegistry in, WorkingSetRegistry out) {
        registry = out;
        readRegistry(in, PlatformUI.PLUGIN_ID,
                IWorkbenchRegistryConstants.PL_WORKINGSETS);
    }
}
