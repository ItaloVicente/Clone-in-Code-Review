		boolean isValid = true;
		List resourcesToExport = getWhiteCheckedResources();
		if (resourcesToExport.isEmpty()){
			setErrorMessage(DataTransferMessages.FileExport_noneSelected);
			isValid =  false;
		} else {
