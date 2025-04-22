			bundleListener = new SynchronousBundleListener() {
				@Override
				public void bundleChanged(BundleEvent event) {
					WorkbenchPlugin.this.bundleChanged(event);
				}
			};
