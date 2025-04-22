	    for (Object element : elements) {
		Object[] children = contentProvider != null ? contentProvider.getChildren(element) : null;
		if (!getGrayed(element) && (children == null || children.length == 0)) {
		    if (!checkState.contains(element)) {
			checkState.add(element);
		    }
