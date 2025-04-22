                changeFont();
            }
        });

        Button preferencePluginButton = new Button(buttonComposite, SWT.PUSH);
        preferencePluginButton.setText("Update Plugin Preferences");
        preferencePluginButton.addSelectionListener(new SelectionListener() {
            @Override
