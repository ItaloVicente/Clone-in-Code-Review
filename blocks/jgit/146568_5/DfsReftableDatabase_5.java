		reftableDatabase = new ReftableDatabase(this::getStack);
	}

	private ReftableStack getStack() throws IOException {
			DfsObjDatabase odb = getRepository().getObjectDatabase();
		if (ctx == null) {
				ctx = odb.newReader();
			}
		return DfsReftableStack.open(ctx
