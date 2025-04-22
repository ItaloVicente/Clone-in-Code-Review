		ImageDescriptor wizardBannerImage = null;
		if (IMPORT.equals(page)) {
			wizardBannerImage = WorkbenchImages.getImageDescriptor(IWorkbenchGraphicConstants.IMG_WIZBAN_IMPORT_WIZ);
			setWindowTitle(WorkbenchMessages.ImportWizard_title);
		} else if (EXPORT.equals(page)) {
			wizardBannerImage = WorkbenchImages.getImageDescriptor(IWorkbenchGraphicConstants.IMG_WIZBAN_EXPORT_WIZ);
			setWindowTitle(WorkbenchMessages.ExportWizard_title);
		}
		if (wizardBannerImage != null) {
