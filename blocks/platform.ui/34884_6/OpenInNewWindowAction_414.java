
package org.eclipse.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.PerspectiveTracker;
import org.eclipse.ui.internal.WorkbenchMessages;

public class NewWizardDropDownAction extends Action implements
        ActionFactory.IWorkbenchAction {

    private IWorkbenchWindow workbenchWindow;
    
    private PerspectiveTracker tracker;

    private ActionFactory.IWorkbenchAction showDlgAction;

    private IContributionItem newWizardMenu;
    
    private IMenuCreator menuCreator = new IMenuCreator() {

        private MenuManager dropDownMenuMgr;

        private void createDropDownMenuMgr() {
            if (dropDownMenuMgr == null) {
                dropDownMenuMgr = new MenuManager();
                dropDownMenuMgr.add(newWizardMenu);
            }
        }

        @Override
		public Menu getMenu(Control parent) {
            createDropDownMenuMgr();
            return dropDownMenuMgr.createContextMenu(parent);
        }

        @Override
		public Menu getMenu(Menu parent) {
            createDropDownMenuMgr();
            Menu menu = new Menu(parent);
            IContributionItem[] items = dropDownMenuMgr.getItems();
            for (int i = 0; i < items.length; i++) {
                IContributionItem item = items[i];
                IContributionItem newItem = item;
                if (item instanceof ActionContributionItem) {
                    newItem = new ActionContributionItem(
                            ((ActionContributionItem) item).getAction());
                }
                newItem.fill(menu, -1);
            }
            return menu;
        }

        @Override
		public void dispose() {
			if (dropDownMenuMgr != null) {
				dropDownMenuMgr.remove(newWizardMenu);

				dropDownMenuMgr.dispose();
				dropDownMenuMgr = null;
			}
        }
    };

    public NewWizardDropDownAction(IWorkbenchWindow window) {
        this(window, ActionFactory.NEW.create(window), ContributionItemFactory.NEW_WIZARD_SHORTLIST.create(window));
    }
    
    public NewWizardDropDownAction(IWorkbenchWindow window,
            ActionFactory.IWorkbenchAction showDlgAction, 
            IContributionItem newWizardMenu) {
        super(WorkbenchMessages.NewWizardDropDown_text);
        if (window == null) {
            throw new IllegalArgumentException();
        }
        this.workbenchWindow = window;
        this.showDlgAction = showDlgAction;
        this.newWizardMenu = newWizardMenu;
        tracker = new PerspectiveTracker(window, this);
        
        setToolTipText(showDlgAction.getToolTipText());

        ISharedImages sharedImages = window.getWorkbench()
                .getSharedImages();
        setImageDescriptor(sharedImages
                .getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD));
        setDisabledImageDescriptor(sharedImages
                .getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD_DISABLED));

        setMenuCreator(menuCreator);
    }

    
    @Override
	public void dispose() {
        if (workbenchWindow == null) {
            return;
        }
        tracker.dispose();
        showDlgAction.dispose();
        newWizardMenu.dispose();
        menuCreator.dispose();
        workbenchWindow = null;
    }

    @Override
	public void run() {
        if (workbenchWindow == null) {
            return;
        }
        showDlgAction.run();
    }

}
