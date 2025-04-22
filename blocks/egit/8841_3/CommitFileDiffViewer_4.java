		IFile file = ResourceUtil.getFileForLocation(getRepository(), p);
		if (file != null && commit != null) {
			if (!CompareUtils.canDirectlyOpenInCompare(file)) {
				try {
					GitModelSynchronize.synchronizeModelWithWorkspace(file,
							getRepository(), commit.getName());
				} catch (Exception e) {
					Activator.logError(UIText.GitHistoryPage_openFailed, e);
					Activator.showError(UIText.GitHistoryPage_openFailed, null);
				}
				return;
			}
		}

		if (file != null)
			next = SaveableCompareEditorInput.createFileElement(file);
