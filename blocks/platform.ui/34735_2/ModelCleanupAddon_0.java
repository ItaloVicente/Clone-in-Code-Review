		Collection<BundleCapability> identities = bundleContext.getBundle(Constants.SYSTEM_BUNDLE_LOCATION).adapt(FrameworkWiring.class).findProviders(req);
		Collection<BundleWiring> result = new ArrayList<BundleWiring>(1); // normally
		for (BundleCapability identity: identities) {
			BundleRevision revision = identity.getRevision();
			BundleWiring wiring = revision.getWiring();
			if (wiring != null) {
				if ((revision.getTypes() & BundleRevision.TYPE_FRAGMENT) != 0) {
					wiring = wiring.getRequiredWires(HostNamespace.HOST_NAMESPACE).get(0).getProviderWiring();
				}
				result.add(wiring);
