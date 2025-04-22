        if (allowAdvanced) {
        		Label spacer = new Label(composite, SWT.NONE);
        		data = new GridData(GridData.GRAB_HORIZONTAL);
        		spacer.setLayoutData(data);
            advancedButton = new Button(composite, SWT.PUSH);
            advancedButton.addSelectionListener(new SelectionAdapter() {

                @Override
				public void widgetSelected(SelectionEvent e) {
                    AdvancedDialog dialog = new AdvancedDialog(parent.getShell());
                }
            });
            advancedButton.setText(ActivityMessages.ActivitiesPreferencePage_advancedButton);
            setButtonLayoutData(advancedButton);
        }
