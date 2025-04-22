		Set<String> relevantBundleNames = contributorMapping.keySet();

		for (Map.Entry<String, TopoSortValue> entry : contributorMapping.entrySet()) {
			entry.getValue().setRelevantRequiresAmount(
					resolveRequires(entry.getKey(), relevantBundleNames));
		}

		List<TopoSortValue> sortedOrder = new ArrayList<TopoSortValue>(contributorMapping.values());
		Collections.sort(sortedOrder);

		int i = 0;
		for (TopoSortValue tsv : sortedOrder) {
			if (i == 0 && tsv.relevantRequiresAmount != 0) {
				logger.warn("Extensions have a cycle"); //$NON-NLS-1$
			}

			for (IExtension ext : tsv.getExtensions()) {
				extensions[i++] = ext;
			}
		}

		assert i == extensions.length;

		return extensions;
	}

	private static int resolveRequires(String bundleSymName, Set<String> relevantBundleNames) {
		Bundle bundle = Activator.getDefault().getBundleForName(bundleSymName);

		Set<String> bundleDep = new HashSet<String>();

		if (bundle != null) {
			BundleWiring wiring = bundle.adapt(BundleWiring.class);
			if (wiring != null) {
				List<BundleWire> wires = new ArrayList<BundleWire>(
						wiring.getRequiredWires(BundleNamespace.BUNDLE_NAMESPACE));
				wires.addAll(wiring.getRequiredWires(PackageNamespace.PACKAGE_NAMESPACE));

				String providerSymName;

				for (BundleWire requiredBundleWire : wires) {
					if (relevantBundleNames.contains(providerSymName = requiredBundleWire
							.getProvider().getSymbolicName())) {
						bundleDep.add(providerSymName);
