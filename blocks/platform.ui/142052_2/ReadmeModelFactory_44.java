	protected void addSections(AdaptableList list, MarkElement element) {
		list.add(element);
		Object[] children = element.getChildren(element);
		for (int i = 0; i < children.length; ++i) {
			addSections(list, (MarkElement) children[i]);
		}
	}
