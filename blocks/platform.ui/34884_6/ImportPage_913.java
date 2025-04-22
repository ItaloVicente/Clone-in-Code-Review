package org.eclipse.ui.internal.dialogs;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.internal.WorkbenchMessages;

public class ImportExportWizard extends Wizard {
	public static final String IMPORT = "import";	//$NON-NLS-1$
	public static final String EXPORT = "export";	//$NON-NLS-1$
		
    private IWorkbench workbench;
    private IStructuredSelection selection;
    private ImportExportPage importExportPage;
    private String page = null;
    
    public ImportExportWizard(String pageId){
    	page = pageId;
    }
    
    @Override
	public boolean performFinish() {
    	importExportPage.saveWidgetValues();
        return true;
    }

    @Override
	public void addPages() {
    	if (page.equals(IMPORT)) {
			importExportPage = new ImportPage(this.workbench, this.selection);
		} else if (page.equals(EXPORT)) {
			importExportPage = new ExportPage(this.workbench, this.selection);
		}
        if (importExportPage != null) {
			addPage(importExportPage);
		}
    }

    public void init(IWorkbench aWorkbench,
            IStructuredSelection currentSelection) {
        this.workbench = aWorkbench;
        this.selection = currentSelection;

        ImageDescriptor wizardBannerImage = null;
        if (IMPORT.equals(page)){
        	wizardBannerImage = WorkbenchImages
                .getImageDescriptor(IWorkbenchGraphicConstants.IMG_WIZBAN_IMPORT_WIZ);
        	setWindowTitle(WorkbenchMessages.ImportWizard_title);
        }
        else if (EXPORT.equals(page)){
        	wizardBannerImage = WorkbenchImages
                    .getImageDescriptor(IWorkbenchGraphicConstants.IMG_WIZBAN_EXPORT_WIZ);
        	setWindowTitle(WorkbenchMessages.ExportWizard_title);
        }
        if (wizardBannerImage != null) {
			setDefaultPageImageDescriptor(wizardBannerImage);
		}
        setNeedsProgressMonitor(true);
    }
}
