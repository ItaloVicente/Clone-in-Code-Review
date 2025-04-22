		boolean showLocationIsSetOnCommandLine = getWorkspaceLocationFromCommandLine(e4Context) != null;
		showLocationInWindowTitle = new Button(groupComposite, SWT.CHECK);
		showLocationInWindowTitle.setText(IDEWorkbenchMessages.IDEWorkspacePreference_showLocationInWindowTitle);
		showLocationInWindowTitle.setSelection(showLocationIsSetOnCommandLine
				|| getIDEPreferenceStore().getBoolean(IDEInternalPreferences.SHOW_LOCATION));
		showLocationInWindowTitle.setEnabled(!showLocationIsSetOnCommandLine);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(showLocationInWindowTitle);

		if (showLocationIsSetOnCommandLine) {
			Composite noteComposite = createNoteComposite(composite.getFont(), groupComposite,
					WorkbenchMessages.Preference_note,
					IDEWorkbenchMessages.IDEWorkspacePreference_showLocationInWindowTitle_lockedByCommandLine);
			GridDataFactory.fillDefaults().grab(true, false).span(2, 1).indent(convertHorizontalDLUsToPixels(12), 0)
					.applyTo(noteComposite);
		}
