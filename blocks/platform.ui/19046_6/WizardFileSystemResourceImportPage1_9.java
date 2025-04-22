        
        this.referenceExistingProjectRadio.setEnabled(isCurrentSourceAnEclipseProject());
        this.referenceAsNewProjectRadio.setEnabled(!isCurrentSourceAnEclipseProject());
        this.copyAsNewProjectRadio.setEnabled(!isCurrentSourceAnEclipseProject());
        if (isCurrentSourceAnEclipseProject() && (this.referenceAsNewProjectRadio.getSelection() || this.copyAsNewProjectRadio.getSelection())) {
        	this.referenceAsNewProjectRadio.setSelection(false);
        	this.copyAsNewProjectRadio.setSelection(false);
        	this.referenceExistingProjectRadio.setSelection(true);
        } else if (!isCurrentSourceAnEclipseProject() && this.referenceAsNewProjectRadio.getSelection()) {
        	this.referenceAsNewProjectRadio.setSelection(true);
        	this.referenceExistingProjectRadio.setSelection(false);
        }
        
        if (this.selectionGroup != null) {
	        enableButtonGroup(ensureSourceIsValid() && this.resourcesRadio.getSelection());
