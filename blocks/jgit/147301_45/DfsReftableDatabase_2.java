		reftableDatabase = new ReftableDatabase() {
			@Override
			public MergedReftable newMergedReftable() throws IOException {
				DfsReftableDatabase.this.getLock().lock();
				try {
					return new MergedReftable(stack().readers());
				} finally {
					DfsReftableDatabase.this.getLock().unlock();
				}
			}
		};
		stack = null;
	}

	protected DfsReftableStack stack() throws IOException {
		assert getLock().isLocked();
		DfsObjDatabase odb = getRepository().getObjectDatabase();

		if (ctx == null) {
			ctx = odb.newReader();
		}
		if (stack == null) {
			stack = DfsReftableStack.open(ctx
		}
		return stack;
