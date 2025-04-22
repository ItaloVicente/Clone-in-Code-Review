		reftableDatabase = new ReftableDatabase() {
			@Override
			public MergedReftable openMergedReftable() throws IOException {
				DfsReftableDatabase.this.getLock().lock();
				try {
					return new MergedReftable(stack().readers());
				} finally {
					DfsReftableDatabase.this.getLock().unlock();
				}
			}
		};
		stack = null;
