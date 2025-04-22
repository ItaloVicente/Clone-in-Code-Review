		reader = null;
		include = new TreeMap<>();
		assume = new HashSet<>();
		tagTargets = new HashSet<>();
	}

	public BundleWriter(ObjectReader or) {
		db = null;
		reader = or;
