				getFieldEditorParent(), 10 * MB, 1 * GB);
		addField(editor);
		editor = new StorageSizeFieldEditor(
				GitCorePreferences.core_textBufferSize,
				UIText.WindowCachePreferencePage_textBufferSizeLabel,
				getFieldEditorParent(), 8 * KB, 128 * KB);
		addField(editor);
		editor.getLabelControl(getFieldEditorParent()).setToolTipText(
				UIText.WindowCachePreferencePage_textBufferSizeTooltip);
