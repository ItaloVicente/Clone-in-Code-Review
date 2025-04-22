	private static void resolveReExports(List<BundleWire> wiresToCheck, List<BundleWire> wires) {
		Set<BundleRevision> alreadyChecked = new HashSet<BundleRevision>();

		Queue<BundleRevision> toCheck = new LinkedList<BundleRevision>();

		for (BundleWire wireToCheck : wiresToCheck) {
			toCheck.offer(wireToCheck.getProvider());
