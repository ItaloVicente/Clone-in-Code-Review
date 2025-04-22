package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.editor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.ui.action.ControlAction;
import org.eclipse.emf.edit.ui.action.CreateChildAction;
import org.eclipse.emf.edit.ui.action.CreateSiblingAction;
import org.eclipse.emf.edit.ui.action.EditingDomainActionBarContributor;
import org.eclipse.emf.edit.ui.action.LoadResourceAction;
import org.eclipse.emf.edit.ui.action.ValidateAction;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.SubContributionItem;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePlugin;

public class HockeyleagueActionBarContributor
	extends EditingDomainActionBarContributor
	implements ISelectionChangedListener {
	protected IEditorPart activeEditorPart;

	protected ISelectionProvider selectionProvider;

	protected IAction showPropertiesViewAction =
		new Action(HockeyleaguePlugin.INSTANCE.getString("_UI_ShowPropertiesView_menu_item")) //$NON-NLS-1$
		{
			public void run() {
				try {
					getPage().showView("org.eclipse.ui.views.PropertySheet"); //$NON-NLS-1$
				}
				catch (PartInitException exception) {
					HockeyleaguePlugin.INSTANCE.log(exception);
				}
			}
		};

	protected IAction refreshViewerAction =
		new Action(HockeyleaguePlugin.INSTANCE.getString("_UI_RefreshViewer_menu_item")) //$NON-NLS-1$
		{
			public boolean isEnabled() {
				return activeEditorPart instanceof IViewerProvider;
			}

			public void run() {
				if (activeEditorPart instanceof IViewerProvider) {
					Viewer viewer = ((IViewerProvider)activeEditorPart).getViewer();
					if (viewer != null) {
						viewer.refresh();
					}
				}
			}
		};

	protected Collection createChildActions;

	protected IMenuManager createChildMenuManager;

	protected Collection createSiblingActions;

	protected IMenuManager createSiblingMenuManager;

	public HockeyleagueActionBarContributor() {
		super(ADDITIONS_LAST_STYLE);
		loadResourceAction = new LoadResourceAction();
		validateAction = new ValidateAction();
		controlAction = new ControlAction();
	}

	public void contributeToToolBar(IToolBarManager toolBarManager) {
		toolBarManager.add(new Separator("hockeyleague-settings")); //$NON-NLS-1$
		toolBarManager.add(new Separator("hockeyleague-additions")); //$NON-NLS-1$
	}

	public void contributeToMenu(IMenuManager menuManager) {
		super.contributeToMenu(menuManager);

		IMenuManager submenuManager = new MenuManager(HockeyleaguePlugin.INSTANCE.getString("_UI_HockeyleagueEditor_menu"), "org.eclipse.ui.examples.views.properties.tabbed.hockeyleagueMenuID"); //$NON-NLS-1$ //$NON-NLS-2$
		menuManager.insertAfter("additions", submenuManager); //$NON-NLS-1$
		submenuManager.add(new Separator("settings")); //$NON-NLS-1$
		submenuManager.add(new Separator("actions")); //$NON-NLS-1$
		submenuManager.add(new Separator("additions")); //$NON-NLS-1$
		submenuManager.add(new Separator("additions-end")); //$NON-NLS-1$

		createChildMenuManager = new MenuManager(HockeyleaguePlugin.INSTANCE.getString("_UI_CreateChild_menu_item")); //$NON-NLS-1$
		submenuManager.insertBefore("additions", createChildMenuManager); //$NON-NLS-1$

		createSiblingMenuManager = new MenuManager(HockeyleaguePlugin.INSTANCE.getString("_UI_CreateSibling_menu_item")); //$NON-NLS-1$
		submenuManager.insertBefore("additions", createSiblingMenuManager); //$NON-NLS-1$

		submenuManager.addMenuListener
			(new IMenuListener() {
				 public void menuAboutToShow(IMenuManager menuManager) {
					 menuManager.updateAll(true);
				 }
			 });

		addGlobalActions(submenuManager);
	}

	public void setActiveEditor(IEditorPart part) {
		super.setActiveEditor(part);
		activeEditorPart = part;

		if (selectionProvider != null) {
			selectionProvider.removeSelectionChangedListener(this);
		}
		if (part == null) {
			selectionProvider = null;
		}
		else {
			selectionProvider = part.getSite().getSelectionProvider();
			selectionProvider.addSelectionChangedListener(this);

			if (selectionProvider.getSelection() != null) {
				selectionChanged(new SelectionChangedEvent(selectionProvider, selectionProvider.getSelection()));
			}
		}
	}

	public void selectionChanged(SelectionChangedEvent event) {
		if (createChildMenuManager != null) {
			depopulateManager(createChildMenuManager, createChildActions);
		}
		if (createSiblingMenuManager != null) {
			depopulateManager(createSiblingMenuManager, createSiblingActions);
		}

		Collection newChildDescriptors = null;
		Collection newSiblingDescriptors = null;

		ISelection selection = event.getSelection();
		if (selection instanceof IStructuredSelection && ((IStructuredSelection)selection).size() == 1) {
			Object object = ((IStructuredSelection)selection).getFirstElement();

			EditingDomain domain = ((IEditingDomainProvider)activeEditorPart).getEditingDomain();

			newChildDescriptors = domain.getNewChildDescriptors(object, null);
			newSiblingDescriptors = domain.getNewChildDescriptors(null, object);
		}

		createChildActions = generateCreateChildActions(newChildDescriptors, selection);
		createSiblingActions = generateCreateSiblingActions(newSiblingDescriptors, selection);

		if (createChildMenuManager != null) {
			populateManager(createChildMenuManager, createChildActions, null);
			createChildMenuManager.update(true);
		}
		if (createSiblingMenuManager != null) {
			populateManager(createSiblingMenuManager, createSiblingActions, null);
			createSiblingMenuManager.update(true);
		}
	}

	protected Collection generateCreateChildActions(Collection descriptors, ISelection selection) {
		Collection actions = new ArrayList();
		if (descriptors != null) {
			for (Iterator i = descriptors.iterator(); i.hasNext(); ) {
				actions.add(new CreateChildAction(activeEditorPart, selection, i.next()));
			}
		}
		return actions;
	}

	protected Collection generateCreateSiblingActions(Collection descriptors, ISelection selection) {
		Collection actions = new ArrayList();
		if (descriptors != null) {
			for (Iterator i = descriptors.iterator(); i.hasNext(); ) {
				actions.add(new CreateSiblingAction(activeEditorPart, selection, i.next()));
			}
		}
		return actions;
	}

	protected void populateManager(IContributionManager manager, Collection actions, String contributionID) {
		if (actions != null) {
			for (Iterator i = actions.iterator(); i.hasNext(); ) {
				IAction action = (IAction)i.next();
				if (contributionID != null) {
					manager.insertBefore(contributionID, action);
				}
				else {
					manager.add(action);
				}
			}
		}
	}
		
	protected void depopulateManager(IContributionManager manager, Collection actions) {
		if (actions != null) {
			IContributionItem[] items = manager.getItems();
			for (int i = 0; i < items.length; i++) {
				IContributionItem contributionItem = items[i];
				while (contributionItem instanceof SubContributionItem) {
					contributionItem = ((SubContributionItem)contributionItem).getInnerItem();
				}

				if (contributionItem instanceof ActionContributionItem) {
					IAction action = ((ActionContributionItem)contributionItem).getAction();
					if (actions.contains(action)) {
						manager.remove(contributionItem);
					}
				}
			}
		}
	}

	public void menuAboutToShow(IMenuManager menuManager) {
		super.menuAboutToShow(menuManager);
		MenuManager submenuManager = null;

		submenuManager = new MenuManager(HockeyleaguePlugin.INSTANCE.getString("_UI_CreateChild_menu_item")); //$NON-NLS-1$
		populateManager(submenuManager, createChildActions, null);
		menuManager.insertBefore("edit", submenuManager); //$NON-NLS-1$

		submenuManager = new MenuManager(HockeyleaguePlugin.INSTANCE.getString("_UI_CreateSibling_menu_item")); //$NON-NLS-1$
		populateManager(submenuManager, createSiblingActions, null);
		menuManager.insertBefore("edit", submenuManager); //$NON-NLS-1$
	}

	protected void addGlobalActions(IMenuManager menuManager) {
		menuManager.insertAfter("additions-end", new Separator("ui-actions")); //$NON-NLS-1$ //$NON-NLS-2$
		menuManager.insertAfter("ui-actions", showPropertiesViewAction); //$NON-NLS-1$

		refreshViewerAction.setEnabled(refreshViewerAction.isEnabled());		
		menuManager.insertAfter("ui-actions", refreshViewerAction); //$NON-NLS-1$

		super.addGlobalActions(menuManager);
	}

	protected boolean removeAllReferencesOnDelete() {
		return true;
	}

}
