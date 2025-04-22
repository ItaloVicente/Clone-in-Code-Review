			Composite dialogAreaComposite = (Composite) super.createDialogArea(parent);

			if (stillOpenElsewhere) {
				Composite checkboxComposite = new Composite(dialogAreaComposite, SWT.NONE);
				checkboxComposite.setLayout(new GridLayout(2, false));

				checkbox = new Button(checkboxComposite, SWT.CHECK);
				checkbox.addSelectionListener(
						widgetSelectedAdapter(e -> dontPromptSelection = checkbox.getSelection()));
				GridData gd = new GridData();
				gd.horizontalAlignment = SWT.BEGINNING;
				checkbox.setLayoutData(gd);

				Label label = new Label(checkboxComposite, SWT.NONE);
				label.setText(WorkbenchMessages.EditorManager_closeWithoutPromptingOption);
				gd = new GridData();
				gd.grabExcessHorizontalSpace = true;
				gd.horizontalAlignment = SWT.BEGINNING;
			}
