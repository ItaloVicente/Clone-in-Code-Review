		try {
			if (bundleListener != null) {
				context.removeBundleListener(bundleListener);
			}
			saveDialogSettings();
			savePreferenceStore();
			preferenceStore = null;
			if (imageRegistry != null)
				imageRegistry.dispose();
			imageRegistry = null;
		} finally {
			super.stop(context);
		}
	}
