
		if (getUnmergedPaths().isEmpty() && !failed()) {
			resultTree = dircache.writeTree(getObjectInserter());
			return true;
		}
		resultTree = null;
		return false;
