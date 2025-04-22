		BundleWiring providerWiring = bundle.adapt(BundleWiring.class);
		if (!providerWiring.isInUse()) {
			return Collections.emptySet();
		}

		Set<Bundle> required = new HashSet<Bundle>();
		addDependents(required, providerWiring);
		return Collections.unmodifiableSet(required);
