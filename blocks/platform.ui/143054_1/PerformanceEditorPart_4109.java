		Object object = null;
		if (useOutline && adapter.equals(IContentOutlinePage.class)) {
			object = new ContentOutlinePage() {
			};
		}
		if (object != null)
