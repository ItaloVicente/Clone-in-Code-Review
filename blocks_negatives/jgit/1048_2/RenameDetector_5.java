		MutableObjectId idBuf = new MutableObjectId();
		while (walk.next()) {
			DiffEntry entry = new DiffEntry();
			walk.getObjectId(idBuf, 0);
			entry.oldId = AbbreviatedObjectId.fromObjectId(idBuf);
			walk.getObjectId(idBuf, 1);
			entry.newId = AbbreviatedObjectId.fromObjectId(idBuf);
			entry.oldMode = walk.getFileMode(0);
			entry.newMode = walk.getFileMode(1);
			entry.newName = entry.oldName = walk.getPathString();
			if (entry.oldMode == FileMode.MISSING) {
				entry.changeType = ChangeType.ADD;
