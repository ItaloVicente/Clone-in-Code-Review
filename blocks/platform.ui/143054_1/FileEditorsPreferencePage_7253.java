				updateSelectedResourceType(); // in case of new default
			}
		}
	}

	public void promptForResourceType() {
		FileExtensionDialog dialog = new FileExtensionDialog(getControl().getShell(),
				WorkbenchMessages.FileExtension_shellTitle, IWorkbenchHelpContextIds.FILE_EXTENSION_DIALOG,
				WorkbenchMessages.FileExtension_dialogTitle, WorkbenchMessages.FileExtension_fileTypeMessage,
