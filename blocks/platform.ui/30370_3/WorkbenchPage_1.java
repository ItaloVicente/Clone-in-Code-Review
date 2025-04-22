		if (desc != null && !desc.isOpenExternal() && isLargeDocument(input)) {
			desc = getAlternateEditor();
			if (desc == null) {
				return null;
			}
		}
		if (desc == null) {
