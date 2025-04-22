   
        referenceAsNewProjectRadio.setSelection(false);
        referenceExistingProjectRadio.setSelection(false);
        resourcesRadio.setSelection(true);
        
        referenceAsNewProjectRadio.addListener(SWT.Selection, this);
        referenceExistingProjectRadio.addListener(SWT.Selection, this);
        resourcesRadio.addListener(SWT.Selection, this);
