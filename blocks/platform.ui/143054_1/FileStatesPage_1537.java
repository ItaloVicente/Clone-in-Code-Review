		Label spacer = new Label(composite, SWT.NONE);
		GridData spacerData = new GridData();
		spacerData.horizontalSpan = 2;
		spacer.setLayoutData(spacerData);

		Composite noteComposite = createNoteComposite(parent.getFont(),
				composite, IDEWorkbenchMessages.Preference_note, IDEWorkbenchMessages.FileHistory_restartNote);
		GridData noteData = new GridData();
		noteData.horizontalSpan = 2;
		noteComposite.setLayoutData(noteData);
