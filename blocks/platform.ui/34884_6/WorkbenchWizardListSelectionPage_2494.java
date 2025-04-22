package org.eclipse.ui.internal.dialogs;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.SelectionEnabler;
import org.eclipse.ui.internal.ISelectionConversionService;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.registry.KeywordRegistry;
import org.eclipse.ui.internal.registry.RegistryReader;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.model.IWorkbenchAdapter2;
import org.eclipse.ui.model.IWorkbenchAdapter3;
import org.eclipse.ui.model.WorkbenchAdapter;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.wizards.IWizardCategory;
import org.eclipse.ui.wizards.IWizardDescriptor;

public class WorkbenchWizardElement extends WorkbenchAdapter implements
        IAdaptable, IPluginContribution, IWizardDescriptor {
    private String id;
    
    private ImageDescriptor imageDescriptor;

    private SelectionEnabler selectionEnabler;

    private IConfigurationElement configurationElement;

    private ImageDescriptor descriptionImage;
    
    private WizardCollectionElement parentCategory;
    
	public static final String TAG_PROJECT = "project"; //$NON-NLS-1$

	private static final String [] EMPTY_TAGS = new String[0];

	private static final String [] PROJECT_TAGS = new String[] {TAG_PROJECT};

	private String[] keywordLabels;

    
    public WorkbenchWizardElement(IConfigurationElement configurationElement) {
        this.configurationElement = configurationElement;
        id = configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
    }

    public boolean canHandleSelection(IStructuredSelection selection) {
        return getSelectionEnabler().isEnabledForSelection(selection);
    }

    @Override
	public IStructuredSelection adaptedSelection(IStructuredSelection selection) {
        if (canHandleSelection(selection)) {
			return selection;
		}

        IStructuredSelection adaptedSelection = convertToResources(selection);
        if (canHandleSelection(adaptedSelection)) {
			return adaptedSelection;
		}

        return StructuredSelection.EMPTY;
    }

    public Object createExecutableExtension() throws CoreException {
        return WorkbenchPlugin.createExtension(configurationElement,
                IWorkbenchRegistryConstants.ATT_CLASS);
    }

    @Override
	public Object getAdapter(Class adapter) {
        if (adapter == IWorkbenchAdapter.class
                || adapter == IWorkbenchAdapter2.class
                || adapter == IWorkbenchAdapter3.class) {
            return this;
        }
        else if (adapter == IPluginContribution.class) {
        	return this;
        }
        else if (adapter == IConfigurationElement.class) {
        	return configurationElement;
        }
        return Platform.getAdapterManager().getAdapter(this, adapter);
    }

    public IConfigurationElement getConfigurationElement() {
        return configurationElement;
    }

    @Override
	public String getDescription() {
        return RegistryReader.getDescription(configurationElement);
    }

    @Override
	public ImageDescriptor getImageDescriptor() {
    	if (imageDescriptor == null) {
    		String iconName = configurationElement
                    .getAttribute(IWorkbenchRegistryConstants.ATT_ICON);
	        if (iconName == null) {
				return null;
			}
            imageDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(
                    configurationElement.getNamespaceIdentifier(), iconName);    
    	}
        return imageDescriptor;
    }

    @Override
	public ImageDescriptor getImageDescriptor(Object element) {
        return getImageDescriptor();
    }
    
    @Override
	public String getLabel(Object element) {
        return configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_NAME);
    }

    protected SelectionEnabler getSelectionEnabler() {
        if (selectionEnabler == null) {
			selectionEnabler = new SelectionEnabler(configurationElement);
		}

        return selectionEnabler;
    }

	private IStructuredSelection convertToResources(
			IStructuredSelection originalSelection) {
		Object selectionService = PlatformUI.getWorkbench().getService(
				ISelectionConversionService.class);
		if (selectionService == null || originalSelection == null) {
			return StructuredSelection.EMPTY;
		}
		return ((ISelectionConversionService) selectionService)
				.convertToResources(originalSelection);
    }

    @Override
	public String getLocalId() {
        return getId();
    }

    @Override
	public String getPluginId() {
        return (configurationElement != null) ? configurationElement
                .getNamespaceIdentifier() : null;
    }

    @Override
	public ImageDescriptor getDescriptionImage() {
    	if (descriptionImage == null) {
    		String descImage = configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_DESCRIPTION_IMAGE);
    		if (descImage == null) {
				return null;
			}
            descriptionImage = AbstractUIPlugin.imageDescriptorFromPlugin(
                    configurationElement.getNamespaceIdentifier(), descImage);
    	}
        return descriptionImage;
    }

    @Override
	public String getHelpHref() {
        return configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_HELP_HREF);
    }
	
	@Override
	public IWorkbenchWizard createWizard() throws CoreException {
		return (IWorkbenchWizard) createExecutableExtension();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getLabel() {		
		return getLabel(this);
	}
	
	@Override
	public IWizardCategory getCategory() {
		return (IWizardCategory) getParent(this);
	}
	
	public WizardCollectionElement getCollectionElement() {
		return (WizardCollectionElement) getParent(this);
	}

	@Override
	public String [] getTags() {
 
        String flag = configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_PROJECT);
        if (Boolean.valueOf(flag).booleanValue()) {
        	return PROJECT_TAGS;
        }
        
        return EMPTY_TAGS;
	}
	
	@Override
	public Object getParent(Object object) {
		return parentCategory;
	}

	public void setParent(WizardCollectionElement parent) {
		parentCategory = parent;
	}

	@Override
	public boolean canFinishEarly() {
		return Boolean.valueOf(configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_CAN_FINISH_EARLY)).booleanValue();
	}

	@Override
	public boolean hasPages() {
		String hasPagesString = configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_HAS_PAGES);
		if (hasPagesString == null) {
			return true;
		}
		return Boolean.valueOf(hasPagesString).booleanValue();
	}

	public String[] getKeywordLabels() {
		if (keywordLabels == null) {

			IConfigurationElement[] children = configurationElement
					.getChildren(IWorkbenchRegistryConstants.TAG_KEYWORD_REFERENCE);
			keywordLabels = new String[children.length];
			KeywordRegistry registry = KeywordRegistry.getInstance();
			for (int i = 0; i < children.length; i++) {
				String id = children[i]
						.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
				keywordLabels[i] = registry.getKeywordLabel(id);
			}
		}
		return keywordLabels;
	}
}
