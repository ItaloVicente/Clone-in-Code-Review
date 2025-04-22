
package org.eclipse.ui.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkingSetComparator;
import org.eclipse.ui.internal.WorkingSetMenuContributionItem;
import org.eclipse.ui.internal.actions.ClearWorkingSetAction;
import org.eclipse.ui.internal.actions.EditWorkingSetAction;
import org.eclipse.ui.internal.actions.SelectWorkingSetAction;
import org.eclipse.ui.internal.util.Util;

public class WorkingSetFilterActionGroup extends ActionGroup {
    private static final String TAG_WORKING_SET_NAME = "workingSetName"; //$NON-NLS-1$

    public static final String CHANGE_WORKING_SET = "changeWorkingSet"; //$NON-NLS-1$
    
    private static final String START_SEPARATOR_ID = "workingSetGroupStartSeparator"; //$NON-NLS-1$

    private static final String SEPARATOR_ID = "workingSetGroupSeparator"; //$NON-NLS-1$

	private static final String WORKING_SET_ACTION_GROUP = "workingSetActionGroup"; //$NON-NLS-1$

    private IWorkingSet workingSet = null;

    private ClearWorkingSetAction clearWorkingSetAction;

    private SelectWorkingSetAction selectWorkingSetAction;

    private EditWorkingSetAction editWorkingSetAction;

    private IPropertyChangeListener workingSetUpdater;

    private IMenuManager menuManager;

	private IWorkbenchWindow workbenchWindow;

	private IWorkbenchPage page;

	private boolean allowWindowWorkingSetByDefault;

	private CompoundContributionItem mruList;

    public WorkingSetFilterActionGroup(Shell shell,
            IPropertyChangeListener workingSetUpdater) {
        Assert.isNotNull(shell);

        this.workingSetUpdater = workingSetUpdater;
        clearWorkingSetAction = new ClearWorkingSetAction(this);
        selectWorkingSetAction = new SelectWorkingSetAction(this, shell);
        editWorkingSetAction = new EditWorkingSetAction(this, shell);
        mruList = new CompoundContributionItem() {

			@Override
			protected IContributionItem[] getContributionItems() {
				IWorkingSet[] workingSets = PlatformUI.getWorkbench()
						.getWorkingSetManager().getRecentWorkingSets();
				List items = new ArrayList(workingSets.length);
				List sortedWorkingSets = Arrays.asList(workingSets);
				Collections.sort(sortedWorkingSets, new WorkingSetComparator());

				int mruMenuCount = 0;
				for (Iterator i = sortedWorkingSets.iterator(); i.hasNext();) {
					IWorkingSet workingSet = (IWorkingSet) i.next();
					if (workingSet != null) {
						IContributionItem item = new WorkingSetMenuContributionItem(
								++mruMenuCount,
								WorkingSetFilterActionGroup.this, workingSet);
						items.add(item);
					}
				}
				return (IContributionItem[]) items
						.toArray(new IContributionItem[items.size()]);
			}
        };
        
        workbenchWindow = Util.getWorkbenchWindowForShell(shell);
        allowWindowWorkingSetByDefault = false;
		page = workbenchWindow.getActivePage();
		if (page == null) {
			IWorkbenchPage[] pages = workbenchWindow.getPages();
			if (pages.length > 0) {
				page = pages[0];
			}
		}
    }
    
    @Override
	public void fillActionBars(IActionBars actionBars) {
        menuManager = actionBars.getMenuManager();
        
        if(menuManager.find(IWorkbenchActionConstants.MB_ADDITIONS) != null)
        	menuManager.insertAfter(IWorkbenchActionConstants.MB_ADDITIONS, new Separator(WORKING_SET_ACTION_GROUP));
        else
        	menuManager.add(new Separator(WORKING_SET_ACTION_GROUP));
        
        menuManager.appendToGroup(WORKING_SET_ACTION_GROUP, selectWorkingSetAction);
        menuManager.appendToGroup(WORKING_SET_ACTION_GROUP, clearWorkingSetAction);
        menuManager.appendToGroup(WORKING_SET_ACTION_GROUP, editWorkingSetAction);
        menuManager.appendToGroup(WORKING_SET_ACTION_GROUP, new Separator(START_SEPARATOR_ID));
        menuManager.appendToGroup(WORKING_SET_ACTION_GROUP, mruList);
        menuManager.appendToGroup(WORKING_SET_ACTION_GROUP, new Separator(SEPARATOR_ID));
    }
    
    
	@Override
	public void fillContextMenu(IMenuManager menuManager) {
		menuManager.add(selectWorkingSetAction);
		menuManager.add(clearWorkingSetAction);
		menuManager.add(editWorkingSetAction);
		menuManager.add(new Separator());
        menuManager.add(mruList);
		menuManager.add(new Separator(SEPARATOR_ID));
	}

    public IWorkingSet getWorkingSet() {
        return workingSet;
    }

    public void setWorkingSet(IWorkingSet newWorkingSet) {
        IWorkingSet oldWorkingSet = workingSet;

        workingSet = newWorkingSet;
        clearWorkingSetAction.setEnabled(newWorkingSet != null);
        editWorkingSetAction.setEnabled(newWorkingSet != null && newWorkingSet.isEditable());

        firePropertyChange(newWorkingSet, oldWorkingSet);
    }

	private void firePropertyChange(IWorkingSet newWorkingSet, IWorkingSet oldWorkingSet) {
        if (workingSetUpdater != null) {
            workingSetUpdater.propertyChange(new PropertyChangeEvent(this,
                    WorkingSetFilterActionGroup.CHANGE_WORKING_SET,
                    oldWorkingSet, newWorkingSet));
        }
	}

	public void saveState(IMemento memento) {
		if (workingSet != null) {
			memento.putString(TAG_WORKING_SET_NAME, workingSet.getName());
		} else {
			memento.putString(TAG_WORKING_SET_NAME, ""); //$NON-NLS-1$
		}
	}

	public void restoreState(IMemento memento) {
		String workingSetName = memento.getString(TAG_WORKING_SET_NAME);
		if (workingSetName != null && workingSetName.length() > 0) {
			setWorkingSet(PlatformUI.getWorkbench().getWorkingSetManager().getWorkingSet(workingSetName));
		} else if (page != null && useWindowWorkingSetByDefault()) {
			setWorkingSet(page.getAggregateWorkingSet());
		}
	}

	private boolean useWindowWorkingSetByDefault() {
		return allowWindowWorkingSetByDefault
				&& PlatformUI
						.getPreferenceStore()
						.getBoolean(
								IWorkbenchPreferenceConstants.USE_WINDOW_WORKING_SET_BY_DEFAULT);
	}
}
