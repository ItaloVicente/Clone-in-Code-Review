	private static Iterable<Bundle> resolveRequiringBundle(Bundle bundle) {
		BundleWiring providerWiring = bundle.adapt(BundleWiring.class);
		if (!providerWiring.isInUse()) {
			return Collections.emptySet();
		}

		Set<Bundle> requiring = new HashSet<Bundle>();

		addRequirers(requiring, providerWiring);
		return Collections.unmodifiableSet(requiring);
	}

	private static void addRequirers(Set<Bundle> requiring, BundleWiring providerWiring) {
		List<BundleWire> requirerWires = providerWiring
				.getProvidedWires(BundleNamespace.BUNDLE_NAMESPACE);
		if (requirerWires == null) {
			return;
		}
		for (BundleWire requireBundleWire : requirerWires) {
			Bundle requirer = requireBundleWire.getRequirer().getBundle();
			if (requiring.contains(requirer)) {
				continue;
			}
			requiring.add(requirer);
			String reExport = requireBundleWire.getRequirement().getDirectives()
					.get(BundleNamespace.REQUIREMENT_VISIBILITY_DIRECTIVE);
			if (BundleNamespace.VISIBILITY_REEXPORT.equals(reExport)) {
				addRequirers(requiring, requireBundleWire.getRequirerWiring());
			}
		}
	}

