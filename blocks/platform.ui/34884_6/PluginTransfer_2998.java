package org.eclipse.ui.part;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

public class PluginDropAdapter extends ViewerDropAdapter {
    public static final String ATT_CLASS = "class";//$NON-NLS-1$

    private TransferData currentTransfer;

    public PluginDropAdapter(StructuredViewer viewer) {
        super(viewer);
    }

    @Override
	public void drop(DropTargetEvent event) {
        try {
            if (PluginTransfer.getInstance().isSupportedType(
                    event.currentDataType)) {
                PluginTransferData pluginData = (PluginTransferData) event.data;
                IDropActionDelegate delegate = getPluginAdapter(pluginData);
                if (!delegate.run(pluginData.getData(), getCurrentTarget())) {
                    event.detail = DND.DROP_NONE;
                }
            } else {
                super.drop(event);
            }
        } catch (CoreException e) {
            WorkbenchPlugin.log("Drop Failed", e.getStatus());//$NON-NLS-1$
        }
    }

    protected TransferData getCurrentTransfer() {
        return currentTransfer;
    }

    protected static IDropActionDelegate getPluginAdapter(
            PluginTransferData data) throws CoreException {

        IExtensionRegistry registry = Platform.getExtensionRegistry();
        String adapterName = data.getExtensionId();
        IExtensionPoint xpt = registry.getExtensionPoint(PlatformUI.PLUGIN_ID,
                IWorkbenchRegistryConstants.PL_DROP_ACTIONS);
        IExtension[] extensions = xpt.getExtensions();
        for (int i = 0; i < extensions.length; i++) {
            IConfigurationElement[] configs = extensions[i].getConfigurationElements();
            if (configs != null && configs.length > 0) {
                for (int j=0; j < configs.length; j++) {
                	String id = configs[j].getAttribute("id");//$NON-NLS-1$
                    if (id != null && id.equals(adapterName)) {
                        return (IDropActionDelegate) WorkbenchPlugin
                                .createExtension(configs[j], ATT_CLASS);
                    }
                }
            }
        }
        return null;
    }
    
    @Override
	public boolean performDrop(Object data) {
        return false;
    }

    @Override
	public boolean validateDrop(Object target, int operation,
            TransferData transferType) {
        currentTransfer = transferType;
        if (currentTransfer != null
                && PluginTransfer.getInstance()
                        .isSupportedType(currentTransfer)) {
            return true;
        }
        return false;
    }
}
