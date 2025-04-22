package org.eclipse.ui.actions;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.swt.widgets.Menu;

public abstract class CompoundContributionItem extends ContributionItem {

    private IMenuListener menuListener = new IMenuListener() {
        @Override
		public void menuAboutToShow(IMenuManager manager) {
            manager.markDirty();
        }
    };
    
    private IContributionItem[] oldItems;
    
    protected CompoundContributionItem() {
        super();
    }

    protected CompoundContributionItem(String id) {
        super(id);
    }
    
    @Override
	public void fill(Menu menu, int index) {
        if (index == -1) {
			index = menu.getItemCount();
		}
        
        IContributionItem[] items = getContributionItemsToFill();
		if (index > menu.getItemCount()) {
			index = menu.getItemCount();
		}
        for (int i = 0; i < items.length; i++) {
            IContributionItem item = items[i];
            int oldItemCount = menu.getItemCount();
            if (item.isVisible()) {
                item.fill(menu, index);
            }
            int newItemCount = menu.getItemCount();
            int numAdded = newItemCount - oldItemCount;
            index += numAdded;
        }
    }
    
    protected abstract IContributionItem[] getContributionItems();
    
    private IContributionItem[] getContributionItemsToFill() {
		disposeOldItems();
		oldItems = getContributionItems();
		return oldItems;
	}

	private void disposeOldItems() {
        if (oldItems != null) {
            for (int i = 0; i < oldItems.length; i++) {
                IContributionItem oldItem = oldItems[i];
                oldItem.dispose();
            }
            oldItems = null;
        }
    }
    
    @Override
	public boolean isDirty() {
		return true;
    }
    
    @Override
	public boolean isDynamic() {
        return true;
    }
    
    
    @Override
	public void setParent(IContributionManager parent) {
        if (getParent() instanceof IMenuManager) {
            IMenuManager menuMgr = (IMenuManager) getParent();
            menuMgr.removeMenuListener(menuListener);
        }
        if (parent instanceof IMenuManager) {
            IMenuManager menuMgr = (IMenuManager) parent;
            menuMgr.addMenuListener(menuListener);
        }
        super.setParent(parent);
    }

	@Override
	public void dispose() {
		disposeOldItems();
		super.dispose();
	}
}
