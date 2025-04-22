	public Object[] filter(Viewer viewer, Object parent, Object[] elements) {
		int size = elements.length;
		ArrayList<Object> out = new ArrayList<Object>(size);
		for (int i = 0; i < size; ++i) {
			Object element = elements[i];
			if (select(viewer, parent, element)) {
