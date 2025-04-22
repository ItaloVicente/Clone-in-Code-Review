	public int category(Object element) {
		return 0;
	}

	public int compare(Viewer viewer, Object e1, Object e2) {
		int cat1 = category(e1);
		int cat2 = category(e2);

		if (cat1 != cat2) {
