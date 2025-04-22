			generator = GENERATOR_NOT_ACTIVE;
			bundle.getBundleContext().addBundleListener(new BundleListener() {
				@Override
				public void bundleChanged(BundleEvent b) {
					if (b.getType() == BundleEvent.STARTED && bundle.getState() == Bundle.ACTIVE) {
						bundle.getBundleContext().removeBundleListener(this);
						putGenerator(element, null);
