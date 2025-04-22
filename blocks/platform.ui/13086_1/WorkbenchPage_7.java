			if (currentPage != null && currentPageOriginalPerspective != null) {
				if (!currentPageOriginalPerspective.equals(currentPage.getActivePerspective())) {
					currentPage.setPerspective(currentPageOriginalPerspective.getDesc());
				}
			}

			if (saveable2Processed) {
				listIterator = dirtyParts.listIterator();
				while (listIterator.hasNext()) {
					ISaveablePart part = (ISaveablePart) listIterator.next();
					if (!part.isDirty()) {
						listIterator.remove();
					}
				}
			}

			modelsToSave = convertToSaveables(dirtyParts, closing, addNonPartSources);

			if (modelsToSave.isEmpty()) {
				return true;
			}
			boolean canceled = SaveableHelper.waitForBackgroundSaveJobs(modelsToSave);
			if (canceled) {
				return false;
			}
			if (modelsToSave.size() == 1) {
				Saveable model = (Saveable) modelsToSave.get(0);
				String message = NLS.bind(WorkbenchMessages.EditorManager_saveChangesQuestion,
						model.getName());
				String[] buttons = new String[] { IDialogConstants.YES_LABEL,
						IDialogConstants.NO_LABEL, IDialogConstants.CANCEL_LABEL };
				MessageDialog d = new MessageDialog(shellProvider.getShell(),
						WorkbenchMessages.Save_Resource, null, message, MessageDialog.QUESTION,
						buttons, 0) {
					protected int getShellStyle() {
						return super.getShellStyle() | SWT.SHEET;
