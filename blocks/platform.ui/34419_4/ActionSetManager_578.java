package org.eclipse.ui.internal;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.SubContributionItem;

public class ActionSetContributionItem extends SubContributionItem implements
        IActionSetContributionItem {

    private String actionSetId;

    public ActionSetContributionItem(IContributionItem item, String actionSetId) {
        super(item);
        this.actionSetId = actionSetId;
    }

    @Override
	public String getActionSetId() {
        return actionSetId;
    }

    @Override
	public void setActionSetId(String newActionSetId) {
        actionSetId = newActionSetId;
    }

	@Override
	public String toString() {
		return "ActionSetContributionItem [id=" + actionSetId + //$NON-NLS-1$
				", visible=" + isVisible() + "]"; //$NON-NLS-1$ //$NON-NLS-2$
	}
}
