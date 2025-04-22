        if (!sourceDirectory.exists() || !sourceDirectory.isDirectory()) {
        	setMessage(DataTransferMessages.FileImport_invalidSource);
        	enableButtonGroup(false);
        	return false;
        }
        
        if (!this.resourcesRadio.getSelection()) {
        	return true;
        }
