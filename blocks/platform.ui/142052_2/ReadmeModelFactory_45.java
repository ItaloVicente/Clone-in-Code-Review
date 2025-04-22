	public AdaptableList getSections(IFile file) {
		MarkElement[] topLevel = getToc(file);
		AdaptableList list = new AdaptableList();
		for (int i = 0; i < topLevel.length; i++) {
			addSections(list, topLevel[i]);
		}
		return list;
	}
