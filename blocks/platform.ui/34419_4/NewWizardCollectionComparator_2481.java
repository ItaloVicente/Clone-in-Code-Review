package org.eclipse.ui.internal.dialogs;

import java.util.StringTokenizer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.wizards.IWizardCategory;
import org.eclipse.ui.wizards.IWizardDescriptor;

public class NewWizard extends Wizard {
    private static final String CATEGORY_SEPARATOR = "/"; //$NON-NLS-1$

    private String categoryId = null;

    private NewWizardSelectionPage mainPage;

    private boolean projectsOnly = false;

    private IStructuredSelection selection;

    private IWorkbench workbench;

    @Override
	public void addPages() {
        IWizardCategory root = WorkbenchPlugin.getDefault().getNewWizardRegistry().getRootCategory();
        IWizardDescriptor [] primary = WorkbenchPlugin.getDefault().getNewWizardRegistry().getPrimaryWizards();

        if (categoryId != null) {
            IWizardCategory categories = root;
            StringTokenizer familyTokenizer = new StringTokenizer(categoryId,
                    CATEGORY_SEPARATOR);
            while (familyTokenizer.hasMoreElements()) {
                categories = getChildWithID(categories, familyTokenizer
                        .nextToken());
                if (categories == null) {
					break;
				}
            }
            if (categories != null) {
				root = categories;
			}
        }

        mainPage = new NewWizardSelectionPage(workbench, selection, root,
				primary, projectsOnly);
        addPage(mainPage);
    }

    public String getCategoryId() {
        return categoryId;
    }

    private IWizardCategory getChildWithID(
            IWizardCategory parent, String id) {
        IWizardCategory [] children = parent.getCategories();
        for (int i = 0; i < children.length; ++i) {
        	IWizardCategory currentChild = children[i];
            if (currentChild.getId().equals(id)) {
				return currentChild;
			}
        }
        return null;
    }

    public void init(IWorkbench aWorkbench,
            IStructuredSelection currentSelection) {
        this.workbench = aWorkbench;
        this.selection = currentSelection;

		if (getWindowTitle() == null) {
			if (projectsOnly) {
				setWindowTitle(WorkbenchMessages.NewProject_title);
			} else {
				setWindowTitle(WorkbenchMessages.NewWizard_title);
			}
		}
        setDefaultPageImageDescriptor(WorkbenchImages
                .getImageDescriptor(IWorkbenchGraphicConstants.IMG_WIZBAN_NEW_WIZ));
        setNeedsProgressMonitor(true);
    }

    @Override
	public boolean performFinish() {
        mainPage.saveWidgetValues();
        if (getContainer().getCurrentPage() == mainPage) {
			if (mainPage.canFinishEarly()) {
				IWizard wizard = mainPage.getSelectedNode().getWizard();
				wizard.setContainer(getContainer());
				return wizard.performFinish();
			}
		}
        return true;
    }

    public void setCategoryId(String id) {
        categoryId = id;
    }

    public void setProjectsOnly(boolean b) {
        projectsOnly = b;
    }
    
    @Override
	public boolean canFinish() {
	    	if (getContainer().getCurrentPage() == mainPage) {
	    		if (mainPage.canFinishEarly()) {
					return true;
				}
	    	}
	    	return super.canFinish();
    }

}
