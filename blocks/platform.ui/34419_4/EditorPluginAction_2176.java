package org.eclipse.ui.internal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IContributionManagerOverrides;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.SubMenuManager;
import org.eclipse.ui.actions.RetargetAction;

public class EditorMenuManager extends SubMenuManager {
    private ArrayList wrappers;

    private boolean enabledAllowed = true;

    private class Overrides implements IContributionManagerOverrides {
        public void updateEnabledAllowed() {
            IContributionItem[] items = EditorMenuManager.super.getItems();
            for (int i = 0; i < items.length; i++) {
                IContributionItem item = items[i];
                item.update(IContributionManagerOverrides.P_ENABLED);
            }
            if (wrappers != null) {
                for (int i = 0; i < wrappers.size(); i++) {
                    EditorMenuManager manager = (EditorMenuManager) wrappers
                            .get(i);
                    manager.setEnabledAllowed(enabledAllowed);
                }
            }
        }

        @Override
		public Boolean getEnabled(IContributionItem item) {
            if (((item instanceof ActionContributionItem) && (((ActionContributionItem) item)
                    .getAction() instanceof RetargetAction))
                    || enabledAllowed) {
				return null;
			} else {
				return Boolean.FALSE;
			}
        }

        @Override
		public Integer getAccelerator(IContributionItem item) {
            if (getEnabled(item) == null) {
				return getParentMenuManager().getOverrides().getAccelerator(
                        item);
			} else {
                return new Integer(0);
			}
        }

        @Override
		public String getAcceleratorText(IContributionItem item) {
            return getParentMenuManager().getOverrides().getAcceleratorText(
                    item);
        }

        @Override
		public String getText(IContributionItem item) {
            return getParentMenuManager().getOverrides().getText(item);
        }
        
        @Override
		public Boolean getVisible(IContributionItem item) {
        	return getParentMenuManager().getOverrides().getVisible(item);
        }
    }

    private Overrides overrides = new Overrides();

	public EditorMenuManager(IMenuManager mgr) {
        super(mgr);
    }

    @Override
	public IContributionItem[] getItems() {
        return getParentMenuManager().getItems();
    }

    @Override
	public IContributionManagerOverrides getOverrides() {
        return overrides;
    }

    @Override
	public void prependToGroup(String groupName, IContributionItem item) {
        insertAfter(groupName, item);
    }

	@Override
	public void appendToGroup(String groupName, IContributionItem item) {
		try {
			super.appendToGroup(groupName, item);
		} catch (RuntimeException e) {
			WorkbenchPlugin.log(e);
		}
	}

    public void setVisible(boolean visible, boolean forceVisibility) {
        if (visible) {
            if (forceVisibility) {
                if (!enabledAllowed) {
					setEnabledAllowed(true);
				}
            } else {
                if (enabledAllowed) {
					setEnabledAllowed(false);
				}
            }
            if (!isVisible()) {
				setVisible(true);
			}
        } else {
            if (forceVisibility) {
                setVisible(false);
			} else {
                setEnabledAllowed(false);
			}
        }
    }

    public void setEnabledAllowed(boolean enabledAllowed) {
        if (this.enabledAllowed == enabledAllowed) {
			return;
		}
        this.enabledAllowed = enabledAllowed;
        overrides.updateEnabledAllowed();
    }

    @Override
	protected SubMenuManager wrapMenu(IMenuManager menu) {
        if (wrappers == null) {
			wrappers = new ArrayList();
		}
		EditorMenuManager manager = new EditorMenuManager(menu);
        wrappers.add(manager);
        return manager;
    }

    protected IAction[] getAllContributedActions() {
        HashSet set = new HashSet();
        getAllContributedActions(set);
        return (IAction[]) set.toArray(new IAction[set.size()]);
    }

    protected void getAllContributedActions(HashSet set) {
        IContributionItem[] items = super.getItems();
        for (int i = 0; i < items.length; i++) {
			getAllContributedActions(set, items[i]);
		}
        if (wrappers == null) {
			return;
		}
        for (Iterator iter = wrappers.iterator(); iter.hasNext();) {
            EditorMenuManager element = (EditorMenuManager) iter.next();
            element.getAllContributedActions(set);
        }
    }

    protected void getAllContributedActions(HashSet set, IContributionItem item) {
        if (item instanceof MenuManager) {
            IContributionItem subItems[] = ((MenuManager) item).getItems();
            for (int j = 0; j < subItems.length; j++) {
				getAllContributedActions(set, subItems[j]);
			}
        } else if (item instanceof ActionContributionItem) {
            set.add(((ActionContributionItem) item).getAction());
        }
    }
}
