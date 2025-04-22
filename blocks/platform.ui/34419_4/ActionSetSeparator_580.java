package org.eclipse.ui.internal;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.SubContributionItem;
import org.eclipse.jface.action.SubMenuManager;

public class ActionSetMenuManager extends SubMenuManager {
    private String actionSetId;

    public ActionSetMenuManager(IMenuManager mgr, String actionSetId) {
        super(mgr);
        this.actionSetId = actionSetId;
    }

    @Override
	public IContributionItem find(String id) {
        IContributionItem item = getParentMenuManager().find(id);
        if (item instanceof SubContributionItem) {
            item = unwrap(item);
		}

        if (item instanceof IMenuManager) {
            IMenuManager menu = (IMenuManager) item;
            if (menu instanceof SubMenuManager) {
                menu = (IMenuManager) ((SubMenuManager) menu).getParent();
			}
            item = getWrapper(menu);
        }

        return item;
    }

	public String getActionSetId() {
		return actionSetId;
	}

    @Override
	public IContributionItem[] getItems() {
        return getParentMenuManager().getItems();
    }

    @Override
	protected SubContributionItem wrap(IContributionItem item) {
        return new ActionSetContributionItem(item, actionSetId);
    }

    @Override
	protected SubMenuManager wrapMenu(IMenuManager menu) {
        return new ActionSetMenuManager(menu, actionSetId);
    }

	@Override
	public String toString() {
		return "ActionSetMenuManager [id=" + actionSetId + "]"; //$NON-NLS-1$ //$NON-NLS-2$
	}
}
