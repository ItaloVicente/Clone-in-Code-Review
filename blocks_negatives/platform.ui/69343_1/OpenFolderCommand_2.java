			if (!structuredSel.isEmpty()) {
				File selectedFile = SmartImportWizard.toFile(structuredSel.getFirstElement());
				if (selectedFile != null) {
					directoryDialog.setFilterPath(selectedFile.getAbsolutePath());
				}
			}
		}
		String res = directoryDialog.open();
		if (res == null) {
			return null;
