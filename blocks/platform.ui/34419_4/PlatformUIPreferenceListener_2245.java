
package org.eclipse.ui.internal;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.internal.provisional.action.IToolBarContributionItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;

final class PlaceholderContributionItem implements IContributionItem {

    private final String id;

    private final int storedHeight;

    private final int storedMinimumItems;

    private final boolean storedUseChevron;

    private final int storedWidth;

    PlaceholderContributionItem(final IToolBarContributionItem item) {
        item.saveWidgetState();
        id = item.getId();
        storedHeight = item.getCurrentHeight();
        storedWidth = item.getCurrentWidth();
        storedMinimumItems = item.getMinimumItemsToShow();
        storedUseChevron = item.getUseChevron();
    }

    @Override
	public void dispose() {
    }

    @Override
	public void fill(Composite parent) {
        throw new UnsupportedOperationException();
    }

    @Override
	public void fill(CoolBar parent, int index) {
        throw new UnsupportedOperationException();

    }

    @Override
	public void fill(Menu parent, int index) {
        throw new UnsupportedOperationException();

    }

    @Override
	public void fill(ToolBar parent, int index) {
        throw new UnsupportedOperationException();

    }

    int getHeight() {
        return storedHeight;
    }

    @Override
	public String getId() {
        return id;
    }

    int getWidth() {
        return storedWidth;
    }
    
    int getMinimumItemsToShow() {
    	return storedMinimumItems;
    }
    
    boolean getUseChevron() {
        return storedUseChevron;
    }

    @Override
	public boolean isDirty() {
        return false;
    }

    @Override
	public boolean isDynamic() {
        return false;
    }

    @Override
	public boolean isEnabled() {
        return false;
    }

    @Override
	public boolean isGroupMarker() {
        return false;
    }

    @Override
	public boolean isSeparator() {
        return false;
    }

    @Override
	public boolean isVisible() {
        return false;
    }

    @Override
	public void saveWidgetState() {

    }

    @Override
	public void setParent(IContributionManager parent) {

    }

    @Override
	public void setVisible(boolean visible) {
    }

    @Override
	public String toString() {
        return "PlaceholderContributionItem(" + id + ")"; //$NON-NLS-1$//$NON-NLS-2$
    }

    @Override
	public void update() {
        update(null);

    }

    @Override
	public void update(String identifier) {
    }
}
