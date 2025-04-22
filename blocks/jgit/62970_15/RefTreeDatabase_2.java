	public RefTreeDatabase(Repository repo
		Config cfg = repo.getConfig();
		String committed = cfg.getString("reftree"
		if (committed == null || committed.isEmpty()) {
		}

		this.repo = repo;
		this.bootstrap = bootstrap;
		this.txnNamespace = initNamespace(committed);
		this.txnCommitted = committed;
	}

