
		if (cnt <= 4096) {
			BlockList<ObjectToPack> tmp = new BlockList<ObjectToPack>(cnt);
			tmp.addAll(objectsLists[Constants.OBJ_COMMIT]);
			tmp.addAll(objectsLists[Constants.OBJ_TREE]);
			tmp.addAll(objectsLists[Constants.OBJ_BLOB]);
			tmp.addAll(objectsLists[Constants.OBJ_TAG]);
			searchForReuse(monitor
			if (pruneCurrentObjectList) {
				pruneEdgesFromObjectList(objectsLists[Constants.OBJ_COMMIT]);
				pruneEdgesFromObjectList(objectsLists[Constants.OBJ_TREE]);
				pruneEdgesFromObjectList(objectsLists[Constants.OBJ_BLOB]);
				pruneEdgesFromObjectList(objectsLists[Constants.OBJ_TAG]);
			}

		} else {
			searchForReuse(monitor
			searchForReuse(monitor
			searchForReuse(monitor
			searchForReuse(monitor
		}

