            setMessage(SOURCE_EMPTY_MESSAGE);
            enableButtonGroup(false);
            return false;
        }

        List resourcesToExport = selectionGroup.getAllWhiteCheckedItems();
        if (resourcesToExport.isEmpty()){
        	setErrorMessage(DataTransferMessages.FileImport_noneSelected);
        	return false;
        }

        enableButtonGroup(true);
        setErrorMessage(null);
        return true;
    }
