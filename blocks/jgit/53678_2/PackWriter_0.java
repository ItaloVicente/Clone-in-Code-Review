		int numCommits = objectsLists[OBJ_COMMIT].size();
		List<ObjectToPack> byName = sortByName();
		sortedByName = null;
		objectsLists = null;
		objectsMap = null;
		writeBitmaps = new PackBitmapIndexBuilder(byName);
		byName = null;

