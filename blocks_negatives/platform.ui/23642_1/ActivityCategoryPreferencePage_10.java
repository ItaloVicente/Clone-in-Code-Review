                workingCopy.setEnabledActivityIds(Collections.EMPTY_SET);
            }
        });
        disableAll.setText(ActivityMessages.ActivityEnabler_deselectAll); 
        setButtonLayoutData(disableAll);
        
        if (allowAdvanced) {
        		Label spacer = new Label(composite, SWT.NONE);
        		data = new GridData(GridData.GRAB_HORIZONTAL);
        		spacer.setLayoutData(data);
            advancedButton = new Button(composite, SWT.PUSH);
            advancedButton.addSelectionListener(new SelectionAdapter() {

                @Override
