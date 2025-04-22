package org.eclipse.ui.internal.wizards.preferences;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.internal.WorkbenchPlugin;


public class PreferencesImportWizard extends Wizard implements IImportWizard {

    private WizardPreferencesImportPage1 mainPage;
	
    public PreferencesImportWizard() {
        IDialogSettings workbenchSettings = WorkbenchPlugin.getDefault().getDialogSettings();
        IDialogSettings section = workbenchSettings
                .getSection("PreferencesImportWizard");//$NON-NLS-1$
        if (section == null) {
			section = workbenchSettings.addNewSection("PreferencesImportWizard");//$NON-NLS-1$
		}
        setDialogSettings(section);
    }

    @Override
	public void addPages() {
        super.addPages();
        mainPage = new WizardPreferencesImportPage1();
        addPage(mainPage);
    }

    @Override
	public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
        setWindowTitle(PreferencesMessages.PreferencesImportWizard_import);
        setDefaultPageImageDescriptor(WorkbenchImages
                .getImageDescriptor(IWorkbenchGraphicConstants.IMG_WIZBAN_IMPORT_PREF_WIZ));
        setNeedsProgressMonitor(true);
    }

    @Override
	public boolean performFinish() {
        return mainPage.finish();
    }
}
