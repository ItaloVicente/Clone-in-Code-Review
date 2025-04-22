    	ISelectionStatusValidator validator = getValidator();
    	Object lowerSelection = getLowerSelectedElement();

    	if (validator != null && lowerSelection != null) {
    		IStatus status = validator.validate(new Object [] {lowerSelection});
    		updateStatus(status);
    		return status.isOK();
    	}
        return super.validateCurrentSelection();
     }
