		return createVersion(dst
	}

	public static int oldestPossibleFormat(
			final List<? extends PackedObjectInfo> objs) {
		for (final PackedObjectInfo oe : objs) {
			if (!PackIndexWriterV1.canStore(oe))
				return 2;
