
package org.eclipse.ui.internal.navigator.wizards;

import java.util.Iterator;

import org.eclipse.core.expressions.ElementHandler;
import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionConverter;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.internal.navigator.NavigatorPlugin;
import org.eclipse.ui.internal.navigator.extensions.INavigatorContentExtPtConstants;

public class CommonWizardDescriptor implements INavigatorContentExtPtConstants, IPluginContribution {
	
	public static final String DEFAULT_MENU_GROUP_ID = "all-uncategorized"; //$NON-NLS-1$

	private String id;
	
	private String wizardId;
	
	private String menuGroupId; 

	private String type;

	private Expression enablement;

	private IConfigurationElement configElement;

	public CommonWizardDescriptor(IConfigurationElement aConfigElement)
			throws WorkbenchException {
		super();
		configElement = aConfigElement;
		init();
	} 

	public CommonWizardDescriptor(IConfigurationElement aConfigElement, String anId)
			throws WorkbenchException {
		super();
		configElement = aConfigElement;
		id = anId;
		init();
		
	}

	public boolean isEnabledFor(IStructuredSelection aStructuredSelection) {
		if (enablement == null) {
			return false;
		}

		IEvaluationContext context = null;
		IEvaluationContext parentContext = NavigatorPlugin.getApplicationContext();

		Iterator elements = aStructuredSelection.iterator();
		while (elements.hasNext()) {
			context = new EvaluationContext(parentContext, elements.next());
			context.setAllowPluginActivation(true);
			if (NavigatorPlugin.safeEvaluate(enablement, context) == EvaluationResult.FALSE) {
				return false;
			}
		}
		return true;
	}

	public boolean isEnabledFor(Object anElement) {
		if (enablement == null) {
			return false;
		}

		IEvaluationContext context = NavigatorPlugin.getEvalContext(anElement);
		return (NavigatorPlugin.safeEvaluate(enablement, context) == EvaluationResult.TRUE);
	}

	void init() throws WorkbenchException { 
		wizardId = configElement.getAttribute(ATT_WIZARD_ID);
		type = configElement.getAttribute(ATT_TYPE);
		
		menuGroupId = configElement.getAttribute(ATT_MENU_GROUP_ID);
		if(menuGroupId == null) {
			menuGroupId = DEFAULT_MENU_GROUP_ID;
		}
		
		if(id == null) {
			id = configElement.getAttribute(ATT_ASSOCIATED_EXTENSION_ID);
		}

		if (wizardId == null || wizardId.length() == 0) {
			throw new WorkbenchException("Missing attribute: " + //$NON-NLS-1$
					ATT_WIZARD_ID + " in common wizard extension: " + //$NON-NLS-1$
					configElement.getDeclaringExtension().getNamespaceIdentifier());
		}

		if (type == null || type.length() == 0) {
			throw new WorkbenchException("Missing attribute: " + //$NON-NLS-1$
					ATT_TYPE + " in common wizard extension: " + //$NON-NLS-1$
					configElement.getDeclaringExtension().getNamespaceIdentifier());
		}

		IConfigurationElement[] children = configElement
				.getChildren(TAG_ENABLEMENT);
		if (children.length == 1) {
			try {
				enablement = ElementHandler.getDefault().create(
						ExpressionConverter.getDefault(), children[0]);
			} catch (CoreException e) {
				NavigatorPlugin.log(IStatus.ERROR, 0, e.getMessage(), e);
			}
		} else if (children.length > 1) {
			throw new WorkbenchException("More than one element: " + //$NON-NLS-1$
					TAG_ENABLEMENT + " in common wizard extension: " + //$NON-NLS-1$
					configElement.getDeclaringExtension().getUniqueIdentifier());
		} 
	}

	public String getWizardId() {
		return wizardId;
	}

	public String getType() {
		return type;
	}
	
	public String getNamespace() {
		return configElement.getDeclaringExtension().getNamespaceIdentifier(); 
	}
	
	public String getId() {
		return id;
	}
	

	public String getMenuGroupId() {
		return menuGroupId;
	}

	
	@Override
	public String toString() {
		return "CommonWizardDescriptor["+getId()+", wizardId="+getWizardId()+"]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	@Override
	public String getLocalId() {
		return getWizardId();
	}
	
	@Override
	public String getPluginId() {
        return (configElement != null) ? configElement.getNamespaceIdentifier() : null;
	}
}
