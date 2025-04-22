	private void searchForReuse(ProgressMonitor monitor
			throws IOException
		pruneCurrentObjectList = false;
		reuseSupport.selectObjectRepresentation(this
		if (pruneCurrentObjectList)
			pruneEdgesFromObjectList(list);
	}

