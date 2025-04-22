package org.eclipse.ui.tests.adaptable;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.actions.AddBookmarkAction;
import org.eclipse.ui.actions.NewWizardMenu;
import org.eclipse.ui.dialogs.PropertyDialogAction;
import org.eclipse.ui.internal.views.navigator.ResourceNavigatorMessages;

public class TestNavigatorActionGroup extends ActionGroup {

    private AdaptedResourceNavigator navigator;

    private AddBookmarkAction addBookmarkAction;

    private PropertyDialogAction propertyDialogAction;


    public TestNavigatorActionGroup(AdaptedResourceNavigator navigator) {
        this.navigator = navigator;
    }

    protected void makeActions() {
        Shell shell = navigator.getSite().getShell();
        addBookmarkAction = new AddBookmarkAction(navigator.getSite(), true);
        propertyDialogAction = new PropertyDialogAction(shell, navigator
                .getViewer());
    }

    @Override
	public void fillContextMenu(IMenuManager menu) {
        IStructuredSelection selection = (IStructuredSelection) getContext()
                .getSelection();

        MenuManager newMenu = new MenuManager(ResourceNavigatorMessages.ResourceNavigator_new);
        menu.add(newMenu);
        newMenu.add(new NewWizardMenu(navigator.getSite().getWorkbenchWindow()));

        addBookmarkAction.selectionChanged(selection);
        menu.add(addBookmarkAction);

        menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
        menu
                .add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS
                        + "-end")); //$NON-NLS-1$
        menu.add(new Separator());

        propertyDialogAction.selectionChanged(selection);
        if (propertyDialogAction.isApplicableForSelection()) {
			menu.add(propertyDialogAction);
		}
    }

    public void fillActionBarMenu(IMenuManager menu,
            IStructuredSelection selection) {
    }

    public void updateGlobalActions(IStructuredSelection selection) {

    }

    public void fillActionBars(IStructuredSelection selection) {
    }

    public void selectionChanged(IStructuredSelection selection) {
    }

}
