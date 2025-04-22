	public SyncOperation sync(Collection<SyncRequest> keys, int replicaCount,
			boolean persist, boolean mutation, boolean pandm,
			OperationCallback cb) {
		return new SyncOperationImpl(keys, replicaCount, persist, mutation, pandm, cb);
	}

