   
        newProjectRadio.setSelection(false);
        existingProjectRadio.setSelection(false);
        resourcesRadio.setSelection(true);
        
        newProjectRadio.addListener(SWT.Selection, this);
        existingProjectRadio.addListener(SWT.Selection, this);
        resourcesRadio.addListener(SWT.Selection, this);
