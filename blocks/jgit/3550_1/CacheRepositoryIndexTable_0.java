
	public void remove(RepositoryName name
			throws DhtException
		db.remove(name

		Sync<Void> sync = Sync.create();
		CacheKey memKey = ns.key(name);
		client.modify(singleton(Change.remove(memKey))
		try {
			sync.get(options.getTimeout());
		} catch (InterruptedException e) {
			throw new TimeoutException();
		}
	}
