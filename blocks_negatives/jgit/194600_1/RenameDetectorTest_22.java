
	private ObjectId blob(String content) throws Exception {
		return testDb.blob(content).copy();
	}

	private static void assertRename(DiffEntry o, DiffEntry n, int score,
			DiffEntry rename) {
		assertEquals(ChangeType.RENAME, rename.getChangeType());

		assertEquals(o.getOldPath(), rename.getOldPath());
		assertEquals(n.getNewPath(), rename.getNewPath());

		assertEquals(o.getOldMode(), rename.getOldMode());
		assertEquals(n.getNewMode(), rename.getNewMode());

		assertEquals(o.getOldId(), rename.getOldId());
		assertEquals(n.getNewId(), rename.getNewId());

		assertEquals(score, rename.getScore());
	}

	private static void assertCopy(DiffEntry o, DiffEntry n, int score,
			DiffEntry copy) {
		assertEquals(ChangeType.COPY, copy.getChangeType());

		assertEquals(o.getOldPath(), copy.getOldPath());
		assertEquals(n.getNewPath(), copy.getNewPath());

		assertEquals(o.getOldMode(), copy.getOldMode());
		assertEquals(n.getNewMode(), copy.getNewMode());

		assertEquals(o.getOldId(), copy.getOldId());
		assertEquals(n.getNewId(), copy.getNewId());

		assertEquals(score, copy.getScore());
	}

	private static void assertAdd(String newName, ObjectId newId,
			FileMode newMode, DiffEntry add) {
		assertEquals(DiffEntry.DEV_NULL, add.oldPath);
		assertEquals(DiffEntry.A_ZERO, add.oldId);
		assertEquals(FileMode.MISSING, add.oldMode);
		assertEquals(ChangeType.ADD, add.changeType);
		assertEquals(newName, add.newPath);
		assertEquals(AbbreviatedObjectId.fromObjectId(newId), add.newId);
		assertEquals(newMode, add.newMode);
	}

	private static void assertDelete(String oldName, ObjectId oldId,
			FileMode oldMode, DiffEntry delete) {
		assertEquals(DiffEntry.DEV_NULL, delete.newPath);
		assertEquals(DiffEntry.A_ZERO, delete.newId);
		assertEquals(FileMode.MISSING, delete.newMode);
		assertEquals(ChangeType.DELETE, delete.changeType);
		assertEquals(oldName, delete.oldPath);
		assertEquals(AbbreviatedObjectId.fromObjectId(oldId), delete.oldId);
		assertEquals(oldMode, delete.oldMode);
	}
