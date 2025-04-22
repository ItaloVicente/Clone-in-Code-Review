package org.eclipse.ui.internal;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.SubMenuManager;
import org.eclipse.jface.action.SubToolBarManager;
import org.eclipse.jface.internal.provisional.action.IToolBarContributionItem;
import org.eclipse.ui.IActionBars2;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.SubActionBars2;
import org.eclipse.ui.internal.provisional.application.IActionBarConfigurer2;
import org.eclipse.ui.services.IServiceLocator;

public class ActionSetActionBars extends SubActionBars2 {
	
	private IActionBarConfigurer2 actionBarConfigurer = null;

	private String actionSetId;

	private ArrayList adjunctContributions = new ArrayList();

	private IToolBarManager coolItemToolBarMgr = null;

	private IToolBarContributionItem toolBarContributionItem = null;

    public ActionSetActionBars(IActionBars2 parent, IServiceLocator serviceLocator, IActionBarConfigurer2 actionBarConfigurer, String actionSetId) {
    	super(parent, serviceLocator);
		this.actionSetId = actionSetId;		
        this.actionBarConfigurer = actionBarConfigurer;
    }

		adjunctContributions.add(item);
	}

	@Override
	protected SubMenuManager createSubMenuManager(IMenuManager parent) {
		return new ActionSetMenuManager(parent, actionSetId);
	}

	@Override
	protected SubToolBarManager createSubToolBarManager(IToolBarManager parent) {
		return null;
	}

	@Override
	public void dispose() {
		super.dispose();
		if (coolItemToolBarMgr == null) {
			return;
		}
		IContributionItem[] items = coolItemToolBarMgr.getItems();
		for (int i = 0; i < items.length; i++) {
			IContributionItem item = items[i];
			if (item instanceof PluginActionCoolBarContributionItem) {
				PluginActionCoolBarContributionItem actionSetItem = (PluginActionCoolBarContributionItem) item;
				if (actionSetItem.getActionSetId().equals(actionSetId)) {
					coolItemToolBarMgr.remove(item);
					item.dispose();
				}
			} else {
			}
		}

		for (int i = 0; i < adjunctContributions.size(); i++) {
			ContributionItem item = (ContributionItem) adjunctContributions
					.get(i);
			IContributionManager parent = item.getParent();
			if (parent != null) {
				parent.remove(item);
				item.dispose();
			}
		}
		toolBarContributionItem = null;
		coolItemToolBarMgr = null;
		adjunctContributions = new ArrayList();
	}

	private IContributionItem findAlphabeticalOrder(String startId,
			String itemId, IContributionManager mgr) {
		IContributionItem[] items = mgr.getItems();
		int insertIndex = 0;

		while (insertIndex < items.length) {
			IContributionItem item = items[insertIndex];
			if (item.getId() != null && item.getId().equals(startId)) {
				break;
			}
			++insertIndex;
		}

		for (int i = insertIndex + 1; i < items.length; i++) {
			IContributionItem item = items[i];
			String testId = item.getId();

			if (item.isGroupMarker()) {
				break;
			}

			if (itemId != null && testId != null) {
				if (itemId.compareTo(testId) < 1) {
					break;
				}
			}
			insertIndex = i;
		}
		if (insertIndex >= items.length) {
			return null;
		}
		return items[insertIndex];
	}

		return actionSetId;
	}

	@Override
	public IToolBarManager getToolBarManager() {
		ICoolBarManager coolBarManager = getCastedParent().getCoolBarManager();
		if (coolBarManager == null) {
			return null;
		}
        return actionBarConfigurer.createToolBarManager();
	}

	public IToolBarManager getToolBarManager(String actionId) {
		String toolBarId = actionSetId;
		boolean isAdjunctType = false;
		if (!actionId.equals(actionSetId)) {
			toolBarId = actionId;
			isAdjunctType = true;
		}

		ICoolBarManager coolBarManager = getCastedParent().getCoolBarManager();
		if (coolBarManager == null) {
			return null;
		}

		if ((coolItemToolBarMgr != null) && (!isAdjunctType)) {
			return coolItemToolBarMgr;
		}

		IContributionItem cbItem = coolBarManager.find(toolBarId);
		if (cbItem instanceof IToolBarContributionItem) {

			IToolBarContributionItem tbcbItem = (IToolBarContributionItem) cbItem;
			coolItemToolBarMgr = tbcbItem.getToolBarManager();
			if (!isAdjunctType) {
				toolBarContributionItem = tbcbItem;
			}
		} else {
			
			coolItemToolBarMgr = actionBarConfigurer.createToolBarManager();
           
            
            IContributionItem toolBarContributionItem = actionBarConfigurer
					.createToolBarContributionItem(coolItemToolBarMgr,
							toolBarId);

			toolBarContributionItem.setParent(coolItemToolBarMgr);
			toolBarContributionItem.setVisible(getActive());
			coolItemToolBarMgr.markDirty();

			IContributionItem refItem = findAlphabeticalOrder(
					IWorkbenchActionConstants.MB_ADDITIONS, toolBarId,
					coolBarManager);
			if (refItem != null) {
				coolBarManager.insertAfter(refItem.getId(),
						toolBarContributionItem);
			} else {
				coolBarManager.add(toolBarContributionItem);
			}
		}
		return coolItemToolBarMgr;
	}

		adjunctContributions.remove(item);
	}

	@Override
	protected void setActive(boolean set) {
		super.setActive(set);

		ICoolBarManager coolBarManager = getCastedParent().getCoolBarManager();
		if (coolBarManager == null) {
			return;
		}

		if (coolItemToolBarMgr != null) {
			IContributionItem[] items = coolItemToolBarMgr.getItems();
			for (int i = 0; i < items.length; i++) {
				IContributionItem item = items[i];
				if (item instanceof PluginActionCoolBarContributionItem) {
					PluginActionCoolBarContributionItem actionSetItem = (PluginActionCoolBarContributionItem) item;
					if (actionSetItem.getActionSetId().equals(actionSetId)) {
						item.setVisible(set);
						coolItemToolBarMgr.markDirty();
						if (!coolBarManager.isDirty()) {
							coolBarManager.markDirty();
						}
					}
				}
			}
			coolItemToolBarMgr.update(false);
			if (toolBarContributionItem != null) {
				toolBarContributionItem.update(ICoolBarManager.SIZE);
			}
		}

		if (adjunctContributions.size() > 0) {
			for (Iterator i = adjunctContributions.iterator(); i.hasNext();) {
				IContributionItem item = (IContributionItem) i.next();
				if (item instanceof ContributionItem) {
					item.setVisible(set);
					IContributionManager manager = ((ContributionItem) item)
							.getParent();
					manager.markDirty();
					manager.update(false);
					if (!coolBarManager.isDirty()) {
						coolBarManager.markDirty();
					}
					item.update(ICoolBarManager.SIZE);
				}

			}

		}
	}

}
