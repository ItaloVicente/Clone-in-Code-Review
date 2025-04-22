	protected ContentOutlinePage getContentOutline() {
		if (userContentOutline == null) {
			userContentOutline = new PropertySheetContentOutlinePage(
					new UserFileParser().parse(getDocumentProvider()));
		}
		return userContentOutline;
	}
