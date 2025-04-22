		if (!SystemReader.getInstance().isWindows()) {
			BooleanFieldEditor mmapEditor = new BooleanFieldEditor(
					GitCorePreferences.core_packedGitMMAP,
					UIText.WindowCachePreferencePage_packedGitMMAP,
					getFieldEditorParent());
			addField(mmapEditor);
		}
