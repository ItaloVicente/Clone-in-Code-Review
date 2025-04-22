        File sourceDirectory = getSourceDirectory();
        if (sourceDirectory == null) {
            setMessage(SOURCE_EMPTY_MESSAGE);
            enableButtonGroup(false);
            return false;
        }

        if (sourceConflictsWithDestination(new Path(sourceDirectory.getPath()))) {
        	setMessage(null);
            setErrorMessage(getSourceConflictMessage());
            enableButtonGroup(false);
            return false;
        }

        List resourcesToExport = selectionGroup.getAllWhiteCheckedItems();
        if (resourcesToExport.isEmpty()){
        	setMessage(null);
        	setErrorMessage(DataTransferMessages.FileImport_noneSelected);
        	return false;
        }
        IContainer container = getSpecifiedContainer();
        if (container != null && container.isVirtual()) {
        	if (ResourcesPlugin.getPlugin().getPluginPreferences().getBoolean(ResourcesPlugin.PREF_DISABLE_LINKING)) {
	        	setMessage(null);
	        	setErrorMessage(DataTransferMessages.FileImport_cannotImportFilesUnderAVirtualFolder);
