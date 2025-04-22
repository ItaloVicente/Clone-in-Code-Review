package org.eclipse.ui.internal;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class ActionSetSeparator extends ContributionItem implements
        IActionSetContributionItem {
    private String actionSetId;

    public ActionSetSeparator(String groupName, String newActionSetId) {
        super(groupName);
        actionSetId = newActionSetId;
    }

    @Override
	public void fill(Menu menu, int index) {
        if (index >= 0) {
			new MenuItem(menu, SWT.SEPARATOR, index);
		} else {
			new MenuItem(menu, SWT.SEPARATOR);
		}
    }

    @Override
	public void fill(ToolBar toolbar, int index) {
        if (index >= 0) {
			new ToolItem(toolbar, SWT.SEPARATOR, index);
		} else {
			new ToolItem(toolbar, SWT.SEPARATOR);
		}
    }

    @Override
	public String getActionSetId() {
        return actionSetId;
    }

    @Override
	public boolean isSeparator() {
        return true;
    }

    @Override
	public void setActionSetId(String newActionSetId) {
        actionSetId = newActionSetId;
    }
}
