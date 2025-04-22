
		useExternal.addListener(SWT.Selection, event -> {
			if (useExternal.getSelection()) {
				prefsManager.setActiveMode(DiffToolMode.EXTERNAL);
				useExternalForType.setEnabled(false);
				prefsManager.setCustomTool(DIFF_TOOL_CUSTOM,
						customDiffCombo.getText());
			}
		});

