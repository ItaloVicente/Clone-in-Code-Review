	private static void resolveReExports(List<BundleWire> wiresToCheck, List<BundleWire> wires) {
		Set<BundleRevision> alreadyChecked = new HashSet<BundleRevision>();

		Deque<BundleRevision> toCheck = new ArrayDeque<BundleRevision>();

		for (BundleWire wireToCheck : wiresToCheck) {
			toCheck.offerLast(wireToCheck.getProvider());
