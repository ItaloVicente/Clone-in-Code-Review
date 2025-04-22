		Set<String> relevantBundleNames = contributorMapping.keySet();

		for (Map.Entry<String, TopoSortValue> entry : contributorMapping.entrySet()) {
			entry.getValue().addDependencies(resolveRequires(entry.getKey(), relevantBundleNames));
		}

		List<TopoSortValue> sortedOrder = new ArrayList<TopoSortValue>(contributorMapping.values());
		Collections.sort(sortedOrder);

		int i = 0;

		for (int tsvIndex = sortedOrder.size() - 1; tsvIndex >= 0; tsvIndex--) {
			TopoSortValue tsv = sortedOrder.get(tsvIndex);
			if (tsv.hasUnresolvedDependencies()) {
				Collections.sort(sortedOrder);
			}

			tsv = sortedOrder.remove(tsvIndex);
			if (tsv.hasUnresolvedDependencies()) {
				logger.warn(
						"Extensions in '{0}' have a cycle to {1}!", new Object[] { tsv.getSymbolicNameOfProvider(), tsv.requiredBundlesToStirng() }); //$NON-NLS-1$
			}

			for (IExtension ext : tsv.getExtensions()) {
				extensions[i++] = ext;
			}

			for (TopoSortValue leftElements : sortedOrder) {
				leftElements.resolvedDependency(tsv.getSymbolicNameOfProvider());
			}
		}

		assert i == extensions.length;

		return extensions;
	}

	private static Set<String> resolveRequires(String bundleSymName, Set<String> relevantBundleNames) {
		Bundle bundle = Activator.getDefault().getBundleForName(bundleSymName);

		Set<String> bundleDep = new HashSet<String>();

		if (bundle != null) {
			BundleWiring wiring = bundle.adapt(BundleWiring.class);
			if (wiring != null) {
				List<BundleWire> requiredBundles = wiring
						.getRequiredWires(BundleNamespace.BUNDLE_NAMESPACE);
				List<BundleWire> wires = new ArrayList<BundleWire>(requiredBundles);
				wires.addAll(wiring.getRequiredWires(PackageNamespace.PACKAGE_NAMESPACE));

				resolveReExports(requiredBundles, wires);

				String providerSymName;

				for (BundleWire requiredBundleWire : wires) {
					if (relevantBundleNames.contains(providerSymName = requiredBundleWire
							.getProvider().getSymbolicName())) {
						bundleDep.add(providerSymName);
