package org.eclipse.ui.internal.decorators;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.internal.ActionExpression;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.registry.RegistryReader;


public abstract class DecoratorDefinition implements IPluginContribution {
	
    private static final String ATT_LABEL = "label"; //$NON-NLS-1$
    
    private static final String ATT_OBJECT_CLASS = "objectClass"; //$NON-NLS-1$
    
    static final String CHILD_ENABLEMENT = "enablement"; //$NON-NLS-1$
    
    private static final String ATT_ADAPTABLE = "adaptable"; //$NON-NLS-1$
    
    private static final String ATT_ENABLED = "state"; //$NON-NLS-1$

    private ActionExpression enablement;

    protected boolean enabled;

    private boolean defaultEnabled;

    private String id;

    protected IConfigurationElement definingElement;

    protected boolean labelProviderCreationFailed = false;

	private boolean hasReadEnablement;

	static final String ATT_CLASS = "class";//$NON-NLS-1$


    DecoratorDefinition(String identifier, IConfigurationElement element) {

        this.id = identifier;  
        this.definingElement = element;
        
        this.enabled = this.defaultEnabled = Boolean.valueOf(element.getAttribute(ATT_ENABLED)).booleanValue();
    }

    public String getName() {
        return definingElement.getAttribute(ATT_LABEL);
    }

    public String getDescription() {
        return RegistryReader.getDescription(definingElement);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean newState) {

        if (this.enabled != newState) {
            this.enabled = newState;
            try {
                refreshDecorator();
            } catch (CoreException exception) {
                handleCoreException(exception);
            }

        }
    }

    protected abstract void refreshDecorator() throws CoreException;

    protected void disposeCachedDecorator(IBaseLabelProvider disposedDecorator) {
        disposedDecorator.removeListener(WorkbenchPlugin.getDefault()
                .getDecoratorManager());
        disposedDecorator.dispose();

    }

    public boolean isAdaptable() {
    	return Boolean.valueOf(definingElement.getAttribute(ATT_ADAPTABLE)).booleanValue();
    }

    public String getId() {
        return id;
    }

    public boolean getDefaultValue() {
        return defaultEnabled;
    }

    protected ActionExpression getEnablement() {
    	if (!hasReadEnablement) {
    		hasReadEnablement = true;
    		initializeEnablement();
    	}
        return enablement;
    }

    protected void initializeEnablement() {
        IConfigurationElement[] elements = definingElement.getChildren(CHILD_ENABLEMENT);
        if (elements.length == 0) {
            String className = definingElement.getAttribute(ATT_OBJECT_CLASS);
            if (className != null) {
				enablement = new ActionExpression(ATT_OBJECT_CLASS,
                        className);
			}
        } else {
			enablement = new ActionExpression(elements[0]);
		}
    }

    void addListener(ILabelProviderListener listener) {
        try {
            IBaseLabelProvider currentDecorator = internalGetLabelProvider();
            if (currentDecorator != null) {
				currentDecorator.addListener(listener);
			}
        } catch (CoreException exception) {
            handleCoreException(exception);
        }
    }

    boolean isLabelProperty(Object element, String property) {
        try { //Internal decorator might be null so be prepared
            IBaseLabelProvider currentDecorator = internalGetLabelProvider();
            if (currentDecorator != null) {
				return currentDecorator.isLabelProperty(element, property);
			}
        } catch (CoreException exception) {
            handleCoreException(exception);
            return false;
        }
        return false;
    }

    protected abstract IBaseLabelProvider internalGetLabelProvider()
            throws CoreException;


    protected void handleCoreException(CoreException exception) {

        WorkbenchPlugin.log(exception);
        crashDisable();
    }

    public void crashDisable() {
        this.enabled = false;
    }

    public abstract boolean isFull();

	public IConfigurationElement getConfigurationElement() {
		return definingElement;
	}

    public boolean isEnabledFor(Object element) {
    	if(isEnabled()){
    		ActionExpression expression =  getEnablement();
    		if(expression != null) {
				return expression.isEnabledFor(element);
			}
    		return true;//Always on if no expression
    	}
    	return false;
       
    }

	@Override
	public String getPluginId() {
		return getConfigurationElement().getContributor().getName();
	}

	@Override
	public String getLocalId() {
		return getId();
	}
}
