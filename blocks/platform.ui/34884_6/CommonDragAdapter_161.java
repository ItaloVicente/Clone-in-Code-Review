package org.eclipse.ui.navigator;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.internal.navigator.extensions.CommonActionExtensionSite;

public abstract class CommonActionProvider extends ActionGroup implements
		IMementoAware {

	private CommonActionExtensionSite actionSite;

	public void init(ICommonActionExtensionSite aSite) {
		actionSite = (CommonActionExtensionSite) aSite;
	}

	protected boolean filterAction(final IAction action) {
		if (actionSite == null) {
			String message = "init() method was not called on CommonActionProvider: " + this + " make sure your init() method calls the superclass"; //$NON-NLS-1$ //$NON-NLS-2$
			throw new IllegalStateException(message);
		}
		
		IPluginContribution piCont = new IPluginContribution() {
			@Override
			public String getLocalId() {
				return action.getId();
			}

			@Override
			public String getPluginId() {
				return actionSite.getPluginId();
			}
		};

		return WorkbenchActivityHelper.filterItem(piCont);
	}
	
	
	@Override
	public void restoreState(IMemento aMemento) {
	}

	@Override
	public void saveState(IMemento aMemento) {
	}

	protected final ICommonActionExtensionSite getActionSite() {
		return actionSite;
	}

}
