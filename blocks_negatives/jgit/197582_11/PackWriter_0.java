			int cnt = 0;
			cnt += objectsLists[OBJ_COMMIT].size();
			cnt += objectsLists[OBJ_TREE].size();
			cnt += objectsLists[OBJ_BLOB].size();
			cnt += objectsLists[OBJ_TAG].size();

			sortedByName = new BlockList<>(cnt);
			sortedByName.addAll(objectsLists[OBJ_COMMIT]);
			sortedByName.addAll(objectsLists[OBJ_TREE]);
			sortedByName.addAll(objectsLists[OBJ_BLOB]);
			sortedByName.addAll(objectsLists[OBJ_TAG]);
			Collections.sort(sortedByName);
