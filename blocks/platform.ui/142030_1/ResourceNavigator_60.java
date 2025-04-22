		ViewerSorter sorter = getTreeViewer().getSorter();
		if (sorter instanceof ResourceSorter) {
			return (ResourceSorter) sorter;
		}
		return null;
	}

