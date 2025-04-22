		BundleRevision bundleRev;
		while (!toCheck.isEmpty()) {
			bundleRev = toCheck.pollFirst();

			if (alreadyChecked.add(bundleRev)) {
				for (BundleWire requiredBundle : bundleRev.getWiring().getRequiredWires(
						BundleNamespace.BUNDLE_NAMESPACE)) {
					if (requiredBundle.getRequirement().getDirectives()
							.get(BundleNamespace.REQUIREMENT_VISIBILITY_DIRECTIVE) == BundleNamespace.VISIBILITY_REEXPORT) {
						wires.add(requiredBundle); // add the wire because it's a Re-exported one

						toCheck.offerLast(requiredBundle.getProvider());
					}
				}
