	public RefTreeDatabase(Repository repo
		Config cfg = repo.getConfig();
		String committed = cfg.getString("reftree"
		if (committed == null || committed.isEmpty()) {
		}

		String namespace = cfg.getString("reftree"
		if (namespace == null || namespace.isEmpty()) {
			int s = committed.lastIndexOf('/');
			if (s > 0) {
				namespace = committed.substring(0
			}
		}

		this.repo = repo;
		this.bootstrap = bootstrap;
		this.txnNamespace = initNamespace(namespace);
		this.txnCommitted = committed;
	}

