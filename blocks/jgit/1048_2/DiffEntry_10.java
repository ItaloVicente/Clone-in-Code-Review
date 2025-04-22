	public static List<DiffEntry> scan(TreeWalk walk) throws IOException {
		List<DiffEntry> r = new ArrayList<DiffEntry>();
		MutableObjectId idBuf = new MutableObjectId();
		while (walk.next()) {
			DiffEntry entry = new DiffEntry();

			walk.getObjectId(idBuf
			entry.oldId = AbbreviatedObjectId.fromObjectId(idBuf);

			walk.getObjectId(idBuf
			entry.newId = AbbreviatedObjectId.fromObjectId(idBuf);

			entry.oldMode = walk.getFileMode(0);
			entry.newMode = walk.getFileMode(1);
			entry.newName = entry.oldName = walk.getPathString();

			if (entry.oldMode == FileMode.MISSING) {
				entry.oldName = DiffEntry.DEV_NULL;
				entry.changeType = ChangeType.ADD;
				r.add(entry);

			} else if (entry.newMode == FileMode.MISSING) {
				entry.newName = DiffEntry.DEV_NULL;
				entry.changeType = ChangeType.DELETE;
				r.add(entry);

			} else {
				entry.changeType = ChangeType.MODIFY;
				if (RenameDetector.sameType(entry.oldMode
					r.add(entry);
				else
					r.addAll(breakModify(entry));
			}
		}
		return r;
	}

	static DiffEntry add(String path
		DiffEntry e = new DiffEntry();
		e.oldId = A_ZERO;
		e.oldMode = FileMode.MISSING;
		e.oldName = DEV_NULL;

		e.newId = AbbreviatedObjectId.fromObjectId(id);
		e.newMode = FileMode.REGULAR_FILE;
		e.newName = path;
		e.changeType = ChangeType.ADD;
		return e;
	}

	static DiffEntry delete(String path
		DiffEntry e = new DiffEntry();
		e.oldId = AbbreviatedObjectId.fromObjectId(id);
		e.oldMode = FileMode.REGULAR_FILE;
		e.oldName = path;

		e.newId = A_ZERO;
		e.newMode = FileMode.MISSING;
		e.newName = DEV_NULL;
		e.changeType = ChangeType.DELETE;
		return e;
	}

	static DiffEntry modify(String path) {
		DiffEntry e = new DiffEntry();
		e.oldMode = FileMode.REGULAR_FILE;
		e.oldName = path;

		e.newMode = FileMode.REGULAR_FILE;
		e.newName = path;
		e.changeType = ChangeType.MODIFY;
		return e;
	}

	static List<DiffEntry> breakModify(DiffEntry entry) {
		DiffEntry del = new DiffEntry();
		del.oldId = entry.getOldId();
		del.oldMode = entry.getOldMode();
		del.oldName = entry.getOldName();

		del.newId = A_ZERO;
		del.newMode = FileMode.MISSING;
		del.newName = DiffEntry.DEV_NULL;
		del.changeType = ChangeType.DELETE;

		DiffEntry add = new DiffEntry();
		add.oldId = A_ZERO;
		add.oldMode = FileMode.MISSING;
		add.oldName = DiffEntry.DEV_NULL;

		add.newId = entry.getNewId();
		add.newMode = entry.getNewMode();
		add.newName = entry.getNewName();
		add.changeType = ChangeType.ADD;
		return Arrays.asList(del
	}

	static DiffEntry pair(ChangeType changeType
			int score) {
		DiffEntry r = new DiffEntry();

		r.oldId = src.oldId;
		r.oldMode = src.oldMode;
		r.oldName = src.oldName;

		r.newId = dst.newId;
		r.newMode = dst.newMode;
		r.newName = dst.newName;

		r.changeType = changeType;
		r.score = score;

		return r;
	}

