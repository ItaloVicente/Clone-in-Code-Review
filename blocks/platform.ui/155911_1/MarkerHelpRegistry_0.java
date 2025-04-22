			BundleContext bundleContext = bundle.getBundleContext();
			if (bundleContext != null) {
				generator = GENERATOR_NOT_ACTIVE;
				bundleContext.addBundleListener(new BundleListener() {
					@Override
					public void bundleChanged(BundleEvent b) {
						if (b.getType() == BundleEvent.STARTED && bundle.getState() == Bundle.ACTIVE) {
							bundleContext.removeBundleListener(this);
							if (getGenerator(element) == GENERATOR_NOT_ACTIVE) {
								putGenerator(element, null);
							}
						}
