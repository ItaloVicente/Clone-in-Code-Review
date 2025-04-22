	public static final DiffEntry NULL;

	static {
		NULL = new DiffEntry();
		NULL.changeType = ChangeType.MODIFY;
		NULL.oldId = NULL.newId = A_ZERO;
		NULL.oldPath = NULL.newPath = DEV_NULL;
		NULL.oldMode = NULL.newMode = FileMode.MISSING;
	}

