		mergeUseExternalTool.addListener(SWT.Selection, event -> {
			prefsManager.setActiveMode(MergeToolMode.EXTERNAL);
			prefsManager.setCustomTool(MERGE_TOOL_CUSTOM,
					customMergeCombo.getText());
		});

