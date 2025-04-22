        		setAdapt(adaptToggle.getSelection());
        	}
        });

        final Button saveNeededToggle = new Button(parent, SWT.CHECK);
        saveNeededToggle.setText("Save on close");
        saveNeededToggle.addSelectionListener(new SelectionAdapter() {
            @Override
