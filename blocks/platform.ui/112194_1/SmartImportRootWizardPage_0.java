	private boolean selectionIsValid() {
		if (tree.getCheckedElements().length == 0) {
			this.proposalSelectionDecorator
					.setDescriptionText(
							DataTransferMessages.SmartImportWizardPage_selectAtLeastOneFolderToOpenAsProject);
			return false;
		}
		for (Object object : tree.getCheckedElements()) {
			if (object instanceof File) {
				File file = (File) object;
				if (ResourcesPlugin.getWorkspace().getRoot().getProject(file.getName()).exists()) {
					this.proposalSelectionDecorator
							.setDescriptionText(
									NLS.bind(DataTransferMessages.SmartImportWizardPage_selectedProjectNameExists,
											file.getName()));
					return false;
				}
			}
		}
		return true;
	}

