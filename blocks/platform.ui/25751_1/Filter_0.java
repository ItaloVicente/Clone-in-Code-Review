	@Override
	public Object[] filter(Viewer viewer, Object parent, Object[] elements) {
		ArrayList result = new ArrayList();
		for (int i = 0; i < elements.length; ++i) {
			if (i % 2 == 1) {
				result.add(elements[i]);
			}
		}
		return result.toArray();
	}
