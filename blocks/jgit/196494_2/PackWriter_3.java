		reallyAdded += 1;
		if (type == OBJ_BLOB) {
			reallyAddedBlobs += 1;
		} else if (type == Constants.OBJ_OFS_DELTA || type == Constants.OBJ_REF_DELTA) {
			reallyAddedOfs += 1;
		} else if (type == OBJ_TREE) {
			reallyAddedTrees += 1;
		}
