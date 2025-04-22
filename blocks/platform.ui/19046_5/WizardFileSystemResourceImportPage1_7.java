        
        this.existingProjectRadio.setEnabled(isCurrentSourceAnEclipseProject());
        this.newProjectRadio.setEnabled(!isCurrentSourceAnEclipseProject());
        if (isCurrentSourceAnEclipseProject() && this.newProjectRadio.getSelection()) {
        	this.newProjectRadio.setSelection(false);
        	this.existingProjectRadio.setSelection(true);
        } else if (!isCurrentSourceAnEclipseProject() && this.existingProjectRadio.getSelection()) {
        	this.newProjectRadio.setSelection(true);
        	this.existingProjectRadio.setSelection(false);
        }
        
        if (this.selectionGroup != null) {
	        enableButtonGroup(ensureSourceIsValid() && this.resourcesRadio.getSelection());
