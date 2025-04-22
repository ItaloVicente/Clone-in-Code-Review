
package org.eclipse.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.LegacyResourceSupport;
import org.eclipse.ui.internal.PerspectiveTracker;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.dialogs.NewWizard;
import org.eclipse.ui.internal.util.Util;

public class NewWizardAction extends Action implements
        ActionFactory.IWorkbenchAction {

    private static final int SIZING_WIZARD_WIDTH = 500;

    private static final int SIZING_WIZARD_HEIGHT = 500;

    private String categoryId = null;

	private String windowTitle = null;

    private IWorkbenchWindow workbenchWindow;

    private PerspectiveTracker tracker;
    
    public NewWizardAction(IWorkbenchWindow window) {
        super(WorkbenchMessages.NewWizardAction_text);
        if (window == null) {
            throw new IllegalArgumentException();
        }
        this.workbenchWindow = window;
        tracker = new PerspectiveTracker(window, this);
        ISharedImages images = PlatformUI.getWorkbench().getSharedImages();
        setImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD));
        setDisabledImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD_DISABLED));
        setToolTipText(WorkbenchMessages.NewWizardAction_toolTip); 
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
				IWorkbenchHelpContextIds.NEW_ACTION);
    }

    @Deprecated
	public NewWizardAction() {
        this(PlatformUI.getWorkbench().getActiveWorkbenchWindow());
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String id) {
        categoryId = id;
    }

	public void setWizardWindowTitle(String windowTitle) {
		this.windowTitle = windowTitle;
	}

    @Override
	public void run() {
        if (workbenchWindow == null) {
            return;
        }
        NewWizard wizard = new NewWizard();
        wizard.setCategoryId(categoryId);
		wizard.setWindowTitle(windowTitle);

        ISelection selection = workbenchWindow.getSelectionService()
                .getSelection();
        IStructuredSelection selectionToPass = StructuredSelection.EMPTY;
        if (selection instanceof IStructuredSelection) {
            selectionToPass = (IStructuredSelection) selection;
        } else {
            Class resourceClass = LegacyResourceSupport.getResourceClass();
            if (resourceClass != null) {
                IWorkbenchPart part = workbenchWindow.getPartService()
                        .getActivePart();
                if (part instanceof IEditorPart) {
                    IEditorInput input = ((IEditorPart) part).getEditorInput();
                    Object resource = Util.getAdapter(input, resourceClass);
                    if (resource != null) {
                        selectionToPass = new StructuredSelection(resource);
                    }
                }
            }
        }

        wizard.init(workbenchWindow.getWorkbench(), selectionToPass);

        IDialogSettings workbenchSettings = WorkbenchPlugin.getDefault()
                .getDialogSettings();
        IDialogSettings wizardSettings = workbenchSettings
                .getSection("NewWizardAction"); //$NON-NLS-1$
        if (wizardSettings == null) {
			wizardSettings = workbenchSettings.addNewSection("NewWizardAction"); //$NON-NLS-1$
		}
        wizard.setDialogSettings(wizardSettings);
        wizard.setForcePreviousAndNextButtons(true);

        Shell parent = workbenchWindow.getShell();
        WizardDialog dialog = new WizardDialog(parent, wizard);
        dialog.create();
        dialog.getShell().setSize(
                Math.max(SIZING_WIZARD_WIDTH, dialog.getShell().getSize().x),
                SIZING_WIZARD_HEIGHT);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(dialog.getShell(),
				IWorkbenchHelpContextIds.NEW_WIZARD);
        dialog.open();
    }

    @Override
	public void dispose() {
        if (workbenchWindow == null) {
            return;
        }
        tracker.dispose();
        workbenchWindow = null;
    }

}
