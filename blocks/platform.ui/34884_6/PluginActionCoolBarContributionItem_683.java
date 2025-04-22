
package org.eclipse.ui.internal;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.ActivityManagerEvent;
import org.eclipse.ui.activities.IActivityManagerListener;
import org.eclipse.ui.activities.IIdentifier;
import org.eclipse.ui.activities.IIdentifierListener;
import org.eclipse.ui.activities.IWorkbenchActivitySupport;
import org.eclipse.ui.activities.IdentifierEvent;
import org.eclipse.ui.activities.WorkbenchActivityHelper;

public class PluginActionContributionItem extends ActionContributionItem
        implements IIdentifierListener, IActivityManagerListener {

    private IIdentifier identifier = null;

    public PluginActionContributionItem(PluginAction action) {
        super(action);
    }

    private void hookListeners() {
        PlatformUI.getWorkbench().getActivitySupport().getActivityManager()
                .addActivityManagerListener(this);
        IIdentifier id = getIdentifier();
        if (id != null) {
			id.addIdentifierListener(this);
		}
    }
    
    private void unhookListeners() {
        PlatformUI.getWorkbench().getActivitySupport().getActivityManager()
                .removeActivityManagerListener(this);

        IIdentifier id = getIdentifier();
        if (id != null) {
			id.removeIdentifierListener(this);
		}
    }
    
    @Override
	public void setParent(IContributionManager parent) {
        IContributionManager oldParent = getParent();
        super.setParent(parent);
        if (oldParent == parent) {
			return;
		}
        
        if (parent == null) {
			unhookListeners();
		} else {
			hookListeners();
		}
    }
    
    private IIdentifier getIdentifier() {
        if (!WorkbenchActivityHelper.isFiltering()) {
			return null;
		}
        
        if (identifier == null) {
            IWorkbenchActivitySupport workbenchActivitySupport = PlatformUI
                    .getWorkbench().getActivitySupport();
            IPluginContribution contribution = (IPluginContribution) getAction();
            identifier = workbenchActivitySupport.getActivityManager()
                    .getIdentifier(
                            WorkbenchActivityHelper
                                    .createUnifiedId(contribution));
        }
        return identifier;
    }

    private void disposeIdentifier() {
        identifier = null;
    }

    @Override
	public void dispose() {
        unhookListeners();
        disposeIdentifier();
    }

    @Override
	public boolean isVisible() {
        if (identifier != null && !identifier.isEnabled()) {
			return false;
		}
        return super.isVisible();
    }

    @Override
	public void identifierChanged(IdentifierEvent identifierEvent) {
        invalidateParent();
    }

	protected void invalidateParent() {
        IContributionManager parent = getParent();
        if (parent != null) {
			parent.markDirty();
		}
    }

    @Override
	public void activityManagerChanged(ActivityManagerEvent activityManagerEvent) {
        if (WorkbenchActivityHelper.isFiltering() && identifier == null) {
            hookListeners();
            invalidateParent();
        } else if (!WorkbenchActivityHelper.isFiltering() && identifier != null) {
            unhookListeners();
            disposeIdentifier();
            invalidateParent();
        }
    }
    
    public ISelection getSelection() {
    	return ((PluginAction)getAction()).getSelection();
    }
}
