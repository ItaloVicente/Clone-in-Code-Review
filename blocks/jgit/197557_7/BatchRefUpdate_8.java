		this.refCache = Optional.empty();
		this.commands = new ArrayList<>();
		this.atomic = refdb.performsAtomicTransactions();
	}

	protected BatchRefUpdate(RefDatabase refdb
		this.refdb = refdb;
		this.refCache = refCache;
