
	private IExtension[] topoSort(IExtension[] extensions) {
		if (extensions.length == 0) {
			return extensions;
		}

		final Map<String, Collection<IExtension>> mappedExtensions = new HashMap<String, Collection<IExtension>>();
		final Map<String, Collection<String>> requires = new HashMap<String, Collection<String>>();
		final Map<String, Collection<String>> depends = new HashMap<String, Collection<String>>();


		for (IExtension extension : extensions) {
			IContributor contributor = extension.getContributor();
			Collection<IExtension> exts = mappedExtensions.get(contributor.getName());
			if (exts == null) {
				mappedExtensions.put(contributor.getName(), exts = new ArrayList<IExtension>());
			}
			exts.add(extension);
			requires.put(contributor.getName(), new HashSet<String>());
			depends.put(contributor.getName(), new HashSet<String>());
		}

		for (String bundleId : mappedExtensions.keySet()) {
			assert requires.containsKey(bundleId) && depends.containsKey(bundleId);

			Bundle requiredBundle = Activator.getDefault().getBundleForName(bundleId);
			if (requiredBundle != null) {
				assert requiredBundle.getSymbolicName().equals(bundleId);
				for (Bundle dependentBundle : resolveRequiringBundle(requiredBundle)) {
					if (!mappedExtensions.containsKey(dependentBundle.getSymbolicName())) {
						continue;
					}
					String depBundleId = dependentBundle.getSymbolicName();
					Collection<String> depBundleReqs = requires.get(depBundleId);
					depBundleReqs.add(bundleId);
					Collection<String> bundleDeps = depends.get(bundleId);
					assert bundleDeps != null;
					bundleDeps.add(depBundleId);
				}
			}
		}

		int resultIndex = 0;

		List<String> sortedByOutdegree = new ArrayList<String>(requires.keySet());
		Comparator<String> outdegreeSorter = new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				assert requires.containsKey(o1) && requires.containsKey(o2);
				return requires.get(o1).size() - requires.get(o2).size();
			}
		};
		Collections.sort(sortedByOutdegree, outdegreeSorter);
		if (!requires.get(sortedByOutdegree.get(0)).isEmpty()) {
			logger.warn("Extensions have a cycle"); //$NON-NLS-1$
		}

		while (!sortedByOutdegree.isEmpty()) {
			if (!requires.get(sortedByOutdegree.get(0)).isEmpty()) {
				Collections.sort(sortedByOutdegree, outdegreeSorter);
			}
			String bundleId = sortedByOutdegree.remove(0);
			assert depends.containsKey(bundleId) && requires.containsKey(bundleId);
			for (IExtension ext : mappedExtensions.get(bundleId)) {
				extensions[resultIndex++] = ext;
			}
			assert requires.get(bundleId).isEmpty();
			requires.remove(bundleId);
			for (String depId : depends.get(bundleId)) {
				requires.get(depId).remove(bundleId);
			}
			depends.remove(bundleId);
		}
		assert resultIndex == extensions.length;
		return extensions;
	}

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

