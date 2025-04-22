		boolean ignoreFileSize = (matchFlags & MATCH_IGNORE_SIZE) != 0;
		if (ignoreFileSize) {
			matchFlags ^= MATCH_IGNORE_SIZE;
		} else {
			if (desc != null && !desc.isOpenExternal()) {
				java.util.Optional<String> largeFileEditorId = largeFileLimitsPreferenceHandler.getEditorForInput(input);
				if (largeFileEditorId == null) {
