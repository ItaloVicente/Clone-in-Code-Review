package org.eclipse.ui.internal.decorators;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.internal.dialogs.DialogUtil;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class LightweightActionDescriptor implements IAdaptable,
        IWorkbenchAdapter {
    private static final Object[] NO_CHILDREN = new Object[0];

    private String id;

    private String label;

    private String description;

    private ImageDescriptor image;

    public LightweightActionDescriptor(IConfigurationElement actionElement) {
        super();

        this.id = actionElement.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
        this.label = actionElement.getAttribute(IWorkbenchRegistryConstants.ATT_LABEL);
        this.description = actionElement
                .getAttribute(IWorkbenchRegistryConstants.TAG_DESCRIPTION);

        String iconName = actionElement.getAttribute(IWorkbenchRegistryConstants.ATT_ICON);
        if (iconName != null) {
            IExtension extension = actionElement.getDeclaringExtension();
            this.image = AbstractUIPlugin.imageDescriptorFromPlugin(extension
                    .getNamespace(), iconName);
        }
    }

    @Override
	public Object getAdapter(Class adapter) {
        if (adapter == IWorkbenchAdapter.class) {
			return this;
		}
        return null;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public ImageDescriptor getImageDescriptor() {
        return image;
    }

    @Override
	public ImageDescriptor getImageDescriptor(Object o) {
        if (o == this) {
			return getImageDescriptor();
		}
        return null;
    }

    public String getLabel() {
        return label;
    }

    @Override
	public String getLabel(Object o) {
        if (o == this) {
            String text = getLabel();
            int end = text.lastIndexOf('@');
            if (end >= 0) {
				text = text.substring(0, end);
			}
            return DialogUtil.removeAccel(text);
        }
        return o == null ? "" : o.toString();//$NON-NLS-1$
    }

    @Override
	public Object[] getChildren(Object o) {
        return NO_CHILDREN;
    }

    @Override
	public Object getParent(Object o) {
        return null;
    }
}
