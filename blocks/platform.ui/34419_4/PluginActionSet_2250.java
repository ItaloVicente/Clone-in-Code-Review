
package org.eclipse.ui.internal;

import java.util.HashSet;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.ui.PlatformUI;

public class PluginActionCoolBarContributionItem extends
        PluginActionContributionItem implements IActionSetContributionItem {
    private String actionSetId;

    public PluginActionCoolBarContributionItem(PluginAction action) {
        super(action);
        setActionSetId(((WWinPluginAction) action).getActionSetId());
    }

    @Override
	public String getActionSetId() {
        return actionSetId;
    }

    @Override
	public void setActionSetId(String id) {
        this.actionSetId = id;
    }

	@Override
	protected void invalidateParent() {
		super.invalidateParent();
		IContributionManager parent = getParent();
		if (parent != null && managersToUpdate.add(parent)) {
			if (!queued) {
				queued = true;
				PlatformUI.getWorkbench().getDisplay().asyncExec(updater);
			}
		}
	}

	private static Runnable updater = new Runnable() {
		@Override
		public void run() {
			IContributionManager[] managers = managersToUpdate
					.toArray(new IContributionManager[managersToUpdate.size()]);
			managersToUpdate.clear();
			queued = false;
			for (IContributionManager manager : managers) {
				manager.update(false);
			}
		}
	};
	private static HashSet<IContributionManager> managersToUpdate = new HashSet<IContributionManager>();
	private static boolean queued = false;
}
