	}

	private void findAllSelectedListElements(Object treeElement, String parentLabel, boolean addAll, IElementFilter filter,
			IProgressMonitor monitor) throws InterruptedException {

		String fullLabel = null;
		if (monitor != null && monitor.isCanceled()) {
