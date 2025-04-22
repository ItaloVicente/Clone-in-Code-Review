        }

        int index = filename.lastIndexOf('.');
        if (index == filename.length() - 1) {
            if (index == 0 || (index == 1 && filename.charAt(0) == '*')) {
                setErrorMessage(WorkbenchMessages.FileExtension_extensionEmptyMessage); 
                return false;
            }
        }

        index = filename.indexOf('*');
        if (index > -1) {
            if (filename.length() == 1) {
                setErrorMessage(WorkbenchMessages.FileExtension_extensionEmptyMessage); 
                return false;
            }
            if (index != 0 || filename.charAt(1) != '.') {
                setErrorMessage(WorkbenchMessages.FileExtension_fileNameInvalidMessage);
                return false;
            }
            if (filename.length() > index && filename.indexOf('*', index + 1) != -1) {
            	setErrorMessage(WorkbenchMessages.FileExtension_fileNameInvalidMessage); 
            	return false;
            }
        }
