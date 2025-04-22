				getFieldEditorParent());
		if (SystemReader.getInstance().isWindows()) {
			mmapEditor.getDescriptionControl(getFieldEditorParent())
					.setToolTipText(
							UIText.WindowCachePreferencePage_mmapToolTipOnWindows);
		}
		addField(mmapEditor);
