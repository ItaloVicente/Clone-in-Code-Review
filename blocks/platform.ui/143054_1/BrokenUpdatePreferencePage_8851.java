				changePluginPreference();
			}
		});

		Button preferenceButton = new Button(buttonComposite, SWT.PUSH);
		preferenceButton.setText("Update Dialog Preferences");
		preferenceButton.addSelectionListener(new SelectionListener() {
			@Override
