	private List<ObjectToPack> makeSortedByName() {
		int cnt = 0;
		cnt += objectsLists[OBJ_COMMIT].size();
		cnt += objectsLists[OBJ_TREE].size();
		cnt += objectsLists[OBJ_BLOB].size();
		cnt += objectsLists[OBJ_TAG].size();

		List<ObjectToPack> allObjects = new BlockList<>(cnt);
		allObjects.addAll(objectsLists[OBJ_COMMIT]);
		allObjects.addAll(objectsLists[OBJ_TREE]);
		allObjects.addAll(objectsLists[OBJ_BLOB]);
		allObjects.addAll(objectsLists[OBJ_TAG]);

		Collections.sort(allObjects);
		return allObjects;
	}

	private void beginPhase(PackingPhase phase
