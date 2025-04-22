		reftableDatabase = new ReftableDatabase() {
			@Override
			public MergedReftable newMergedReftable() throws IOException {
				return new MergedReftable(stack().readers());
			}
		};
		stack = null;
	}

	protected DfsReftableStack stack() throws IOException {
			getLock().lock();
			try {
				DfsObjDatabase odb = getRepository().getObjectDatabase();

				if (ctx == null) {
					ctx = odb.newReader();
				}
				if (stack == null) {
					stack = DfsReftableStack.open(ctx
				}
				return stack;
			} finally {
				getLock().unlock();
			}
