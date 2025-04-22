    @Override
	public boolean hasChildren(java.lang.Object element) {
        if (element instanceof IViewRegistry) {
			return true;
		} else if (element instanceof IViewCategory) {
            if (getChildren(element).length > 0) {
