		@Override
		public boolean performsAtomicTransactions() {
			return performsAtomicTransactions;
		}

		@Override
		public BatchRefUpdate newBatchUpdate() {
			return new BatchRefUpdate(this) {
				@Override
				public void execute(RevWalk walk
						throws IOException {
					if (performsAtomicTransactions()) {
						try {
							lock.writeLock().lock();
							batch(walk
						} finally {
							lock.writeLock().unlock();
						}
					} else {
						super.execute(walk
					}
				}
			};
		}

