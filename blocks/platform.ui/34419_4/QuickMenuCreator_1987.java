package org.eclipse.ui.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ibm.icu.text.Collator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;

import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.statushandlers.StatusManager;

public abstract class PerspectiveMenu extends ContributionItem {
	@Deprecated
	protected static final String SHOW_PERSP_ID = IWorkbenchCommandConstants.PERSPECTIVES_SHOW_PERSPECTIVE;

	private IPerspectiveRegistry reg;

    private IWorkbenchWindow window;

    private boolean showActive = false;

    private boolean dirty = true;

    private IMenuListener menuListener = new IMenuListener() {
        @Override
		public void menuAboutToShow(IMenuManager manager) {
            manager.markDirty();
            dirty = true;
        }
    };

    private Comparator comparator = new Comparator() {
        private Collator collator = Collator.getInstance();

        @Override
		public int compare(Object ob1, Object ob2) {
            IPerspectiveDescriptor d1 = (IPerspectiveDescriptor) ob1;
            IPerspectiveDescriptor d2 = (IPerspectiveDescriptor) ob2;
            return collator.compare(d1.getLabel(), d2.getLabel());
        }
    };

    private static final String NO_TARGETS_MSG = WorkbenchMessages.Workbench_showInNoPerspectives;

    private Map actions = new HashMap();

    private Action openOtherAction = new Action(WorkbenchMessages.PerspectiveMenu_otherItem) {
        @Override
		public final void runWithEvent(final Event event) {
            runOther(new SelectionEvent(event));
        }
    };


    public PerspectiveMenu(IWorkbenchWindow window, String id) {
        super(id);
        this.window = window;
        reg = window.getWorkbench().getPerspectiveRegistry();
		openOtherAction
				.setActionDefinitionId(IWorkbenchCommandConstants.PERSPECTIVES_SHOW_PERSPECTIVE);
    }

    @Override
	public void fill(Menu menu, int index) {
        if (getParent() instanceof MenuManager) {
			((MenuManager) getParent()).addMenuListener(menuListener);
		}

        if (!dirty) {
			return;
		}

        final MenuManager manager = new MenuManager();
        fillMenu(manager);
        final IContributionItem items[] = manager.getItems();
        if (items.length == 0) {
            MenuItem item = new MenuItem(menu, SWT.NONE, index++);
            item.setText(NO_TARGETS_MSG);
            item.setEnabled(false);
        } else {
            for (int i = 0; i < items.length; i++) {
                items[i].fill(menu, index++);
            }
        }
        dirty = false;
    }

    private final void fillMenu(final MenuManager manager) {
        manager.removeAll();

        final List persps = getPerspectiveItems();
        Collections.sort(persps, comparator);

        final List actions = new ArrayList(persps.size());
        for (Iterator i = persps.iterator(); i.hasNext();) {
            final IPerspectiveDescriptor descriptor = (IPerspectiveDescriptor) i
                    .next();
            final IAction action = getAction(descriptor.getId());
            if (action != null) {
                if (WorkbenchActivityHelper.filterItem(action)) {
					continue;
				}
                actions.add(action);
            }
        }

        for (Iterator i = actions.iterator(); i.hasNext();) {
            manager.add((IAction) i.next());
        }

        if (PrefUtil
                .getAPIPreferenceStore()
                .getBoolean(
                        IWorkbenchPreferenceConstants.SHOW_OTHER_IN_PERSPECTIVE_MENU)) {
        	if (actions.size() > 0) {
				manager.add(new Separator());
			}
			manager.add(openOtherAction);
        }
    }

    private final IAction getAction(final String id) {
        IAction action = (IAction) actions.get(id);
        if (action == null) {
            IPerspectiveDescriptor descriptor = reg.findPerspectiveWithId(id);
            if (descriptor != null) {
                action = new OpenPerspectiveAction(window, descriptor, this);
                action.setActionDefinitionId(id);
                actions.put(id, action);
            }
        }
        return action;
    }

    private ArrayList getPerspectiveShortcuts() {
        ArrayList list = new ArrayList();

        IWorkbenchPage page = window.getActivePage();
        if (page == null) {
			return list;
		}

        String[] ids = page.getPerspectiveShortcuts();

        for (int i = 0; i < ids.length; i++) {
            IPerspectiveDescriptor desc = reg.findPerspectiveWithId(ids[i]);
            if (desc != null && !list.contains(desc)) {
                if (WorkbenchActivityHelper.filterItem(desc)) {
					continue;
				}
                list.add(desc);
            }
        }

        return list;
    }

    protected ArrayList getPerspectiveItems() {
        ArrayList shortcuts = getPerspectiveShortcuts();
        ArrayList list = new ArrayList(shortcuts.size());

        int size = shortcuts.size();
        for (int i = 0; i < size; i++) {
            if (!list.contains(shortcuts.get(i))) {
                list.add(shortcuts.get(i));
            }
        }

        return list;
    }

    protected boolean getShowActive() {
        return showActive;
    }

    protected IWorkbenchWindow getWindow() {
        return window;
    }

    @Override
	public boolean isDirty() {
        return dirty;
    }

    @Override
	public boolean isDynamic() {
        return true;
    }

    protected abstract void run(IPerspectiveDescriptor desc);

    protected void run(IPerspectiveDescriptor desc, SelectionEvent event) {
        run(desc);
    }

    void runOther(SelectionEvent event) {
		IHandlerService handlerService = window
				.getService(IHandlerService.class);
		try {
			handlerService.executeCommand(IWorkbenchCommandConstants.PERSPECTIVES_SHOW_PERSPECTIVE, null);
		} catch (ExecutionException e) {
			StatusManager.getManager().handle(
					new Status(IStatus.WARNING, WorkbenchPlugin.PI_WORKBENCH,
							"Failed to execute " + IWorkbenchCommandConstants.PERSPECTIVES_SHOW_PERSPECTIVE, e)); //$NON-NLS-1$
		} catch (NotDefinedException e) {
			StatusManager.getManager().handle(
					new Status(IStatus.WARNING, WorkbenchPlugin.PI_WORKBENCH,
							"Failed to execute " + IWorkbenchCommandConstants.PERSPECTIVES_SHOW_PERSPECTIVE, e)); //$NON-NLS-1$
		} catch (NotEnabledException e) {
			StatusManager.getManager().handle(
					new Status(IStatus.WARNING, WorkbenchPlugin.PI_WORKBENCH,
							"Failed to execute " + IWorkbenchCommandConstants.PERSPECTIVES_SHOW_PERSPECTIVE, e)); //$NON-NLS-1$
		} catch (NotHandledException e) {
			StatusManager.getManager().handle(
					new Status(IStatus.WARNING, WorkbenchPlugin.PI_WORKBENCH,
							"Failed to execute " + IWorkbenchCommandConstants.PERSPECTIVES_SHOW_PERSPECTIVE, e)); //$NON-NLS-1$
		}
	}

    protected void showActive(boolean b) {
        showActive = b;
    }
}
