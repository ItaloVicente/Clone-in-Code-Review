		BundleWiring providerWiring = bundle.adapt(BundleWiring.class);
		if (!providerWiring.isInUse()) {
			return Collections.emptySet();
		}

		Set<Bundle> requiring = new HashSet<Bundle>();

		addRequirers(requiring, providerWiring);
		return Collections.unmodifiableSet(requiring);
