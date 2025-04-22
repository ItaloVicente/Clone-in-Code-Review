            text = new Label(composite, SWT.NONE);
			text
					.setText(strings
							.getProperty(
									WorkbenchTriggerPointAdvisor.PROCEED_SINGLE,
									RESOURCE_BUNDLE
											.getString(WorkbenchTriggerPointAdvisor.PROCEED_SINGLE)));
            text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            text.setFont(dialogFont);
        } else {
