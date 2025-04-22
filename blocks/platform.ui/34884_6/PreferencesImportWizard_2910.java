package org.eclipse.ui.internal.wizards.preferences;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.internal.WorkbenchPlugin;

public class PreferencesExportWizard extends Wizard implements IExportWizard {

    private WizardPreferencesExportPage1 mainPage;

    public PreferencesExportWizard() {
        IDialogSettings workbenchSettings = WorkbenchPlugin.getDefault().getDialogSettings();
        IDialogSettings section = workbenchSettings
                .getSection("PreferencesExportWizard");//$NON-NLS-1$
        if (section == null) {
			section = workbenchSettings.addNewSection("PreferencesExportWizard");//$NON-NLS-1$
		}
        setDialogSettings(section);
    }

    @Override
	public void addPages() {
        super.addPages();
        mainPage = new WizardPreferencesExportPage1();
        addPage(mainPage);
    }

    @Override
	public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
        setWindowTitle(PreferencesMessages.PreferencesExportWizard_export);
        setDefaultPageImageDescriptor(WorkbenchImages
                .getImageDescriptor(IWorkbenchGraphicConstants.IMG_WIZBAN_EXPORT_PREF_WIZ));
        setNeedsProgressMonitor(true);
    }

    @Override
	public boolean performFinish() {
        return mainPage.finish();
    }
    
    
}
