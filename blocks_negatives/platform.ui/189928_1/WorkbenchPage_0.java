		if (desc != null && !desc.isOpenExternal()) {
			java.util.Optional<String> largeFileEditorId = largeFileLimitsPreferenceHandler.getEditorForInput(input);
			if (largeFileEditorId == null) {
				return null;
			}
			if (largeFileEditorId.isPresent()) {
				editorId = largeFileEditorId.get();
				desc = editorRegistry.findEditor(editorId);
				if (desc instanceof EditorDescriptor && desc.isOpenExternal()) {
					openExternalEditor((EditorDescriptor) desc, input);
