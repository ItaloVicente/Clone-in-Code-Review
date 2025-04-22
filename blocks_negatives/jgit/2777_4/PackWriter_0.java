		for (List<ObjectToPack> list : objectsLists) {
			pruneCurrentObjectList = false;
			reuseSupport.selectObjectRepresentation(this, monitor, list);
			if (pruneCurrentObjectList)
				pruneEdgesFromObjectList(list);
		}
