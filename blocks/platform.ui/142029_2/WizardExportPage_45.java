		}

		return false;
	}

	protected void internalSaveWidgetValues() {
	}

	protected Object[] queryIndividualResourcesToExport(IAdaptable rootResource) {
		ResourceSelectionDialog dialog = new ResourceSelectionDialog(getContainer().getShell(), rootResource,
				IDEWorkbenchMessages.WizardExportPage_selectResourcesTitle);
		dialog.setInitialSelections(selectedResources.toArray(new Object[selectedResources.size()]));
		dialog.open();
		return dialog.getResult();
	}

	protected Object[] queryResourceTypesToExport() {
		IFileEditorMapping editorMappings[] = PlatformUI.getWorkbench().getEditorRegistry().getFileEditorMappings();

		int mappingsSize = editorMappings.length;
		List selectedTypes = getTypesToExport();
		List initialSelections = new ArrayList(selectedTypes.size());

		for (int i = 0; i < mappingsSize; i++) {
			IFileEditorMapping currentMapping = editorMappings[i];
			if (selectedTypes.contains(currentMapping.getExtension())) {
