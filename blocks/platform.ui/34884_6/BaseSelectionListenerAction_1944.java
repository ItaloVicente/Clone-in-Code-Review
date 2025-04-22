package org.eclipse.ui.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IRegistryChangeEvent;
import org.eclipse.core.runtime.IRegistryChangeListener;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.dynamichelpers.IExtensionChangeHandler;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.WorkbenchWindow;
import org.eclipse.ui.internal.actions.NewWizardShortcutAction;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.wizards.IWizardDescriptor;

public class BaseNewWizardMenu extends CompoundContributionItem {

    private final Map actions = new HashMap(21);

    private final IExtensionChangeHandler configListener = new IExtensionChangeHandler() {

        @Override
		public void removeExtension(IExtension source, Object[] objects) {
            for (int i = 0; i < objects.length; i++) {
                if (objects[i] instanceof NewWizardShortcutAction) {
                    actions.values().remove(objects[i]);
                }
            }
        }

        @Override
		public void addExtension(IExtensionTracker tracker, IExtension extension) {
        }
    };

    private final IRegistryChangeListener registryListener = new IRegistryChangeListener() {

        @Override
		public void registryChanged(IRegistryChangeEvent event) {
            if (getParent() != null) {
                getParent().markDirty();
            }
        }

    };

    private ActionFactory.IWorkbenchAction showDlgAction;

    private IWorkbenchWindow workbenchWindow;

    public BaseNewWizardMenu(IWorkbenchWindow window, String id) {
        super(id);
        Assert.isNotNull(window);
        this.workbenchWindow = window;
        showDlgAction = ActionFactory.NEW.create(window);
        registerListeners();
		if (window instanceof WorkbenchWindow) {
			((WorkbenchWindow) window)
					.addSubmenu(WorkbenchWindow.NEW_WIZARD_SUBMENU);
		}
    }

    protected void addItems(List list) {
        if (addShortcuts(list)) {
            list.add(new Separator());
        }
        list.add(new ActionContributionItem(getShowDialogAction()));
    }

    protected boolean addShortcuts(List list) {
        boolean added = false;
        IWorkbenchPage page = workbenchWindow.getActivePage();
        if (page != null) {
            String[] wizardIds = page.getNewWizardShortcuts();
            for (int i = 0; i < wizardIds.length; i++) {
                IAction action = getAction(wizardIds[i]);
                if (action != null) {
                    if (!WorkbenchActivityHelper.filterItem(action)) {
                        list.add(new ActionContributionItem(action));
                        added = true;
                    }
                }
            }
        }
        return added;
    }

    @Override
	public void dispose() {
        if (workbenchWindow != null) {
            super.dispose();
            unregisterListeners();
            showDlgAction.dispose();
            showDlgAction = null;
            workbenchWindow = null;
        }
    }

    private IAction getAction(String id) {
        IAction action = (IAction) actions.get(id);
        if (action == null) {
            IWizardDescriptor wizardDesc = WorkbenchPlugin.getDefault()
					.getNewWizardRegistry().findWizard(id);
            if (wizardDesc != null) {
                action = new NewWizardShortcutAction(workbenchWindow,
						wizardDesc);
				actions.put(id, action);
				IConfigurationElement element = (IConfigurationElement) Util
						.getAdapter(wizardDesc, IConfigurationElement.class);
				if (element != null) {
					workbenchWindow.getExtensionTracker().registerObject(
							element.getDeclaringExtension(), action,
							IExtensionTracker.REF_WEAK);
				}
            }
        }
        return action;
    }

    @Override
	protected IContributionItem[] getContributionItems() {
        ArrayList list = new ArrayList();
        if (workbenchWindow != null && workbenchWindow.getActivePage() != null
                && workbenchWindow.getActivePage().getPerspective() != null) {
            addItems(list);
        } else {
            String text = WorkbenchMessages.Workbench_noApplicableItems;
            Action dummyAction = new Action(text) {
            };
            dummyAction.setEnabled(false);
            list.add(new ActionContributionItem(dummyAction));
        }
        return (IContributionItem[]) list.toArray(new IContributionItem[list.size()]);
    }

    protected IAction getShowDialogAction() {
        return showDlgAction;
    }

    protected IWorkbenchWindow getWindow() {
        return workbenchWindow;
    }

    private void registerListeners() {
        Platform.getExtensionRegistry().addRegistryChangeListener(
                registryListener);
        workbenchWindow.getExtensionTracker().registerHandler(
				configListener,  null);
    }

    protected boolean registryHasCategory(String categoryId) {
    	return WorkbenchPlugin.getDefault().getNewWizardRegistry()
				.findCategory(categoryId) != null;
    }

    private void unregisterListeners() {
        Platform.getExtensionRegistry().removeRegistryChangeListener(
                registryListener);
        workbenchWindow.getExtensionTracker().unregisterHandler(configListener);
    }
}
