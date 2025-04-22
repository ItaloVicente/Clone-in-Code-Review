	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		if (WorkbenchMessages.ICategory_other.equals(e1)) {
			return -1;
		}
		if (WorkbenchMessages.ICategory_general.equals(e2)) {
			return 1;
		}
