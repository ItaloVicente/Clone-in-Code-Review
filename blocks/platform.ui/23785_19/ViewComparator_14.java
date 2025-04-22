	@SuppressWarnings("restriction")
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		if (WorkbenchSWTMessages.ICategory_general.equals(e1)) {
			return -1;
		}
		if (WorkbenchSWTMessages.ICategory_general.equals(e2)) {
			return 1;
		}
