		return ((File) element).isDirectory();
	}

	public void clearVisitedDirs() {
		if(visitedDirs!=null)
			visitedDirs.clear();
	}
