		declareImages();
	}

	public static void dispose() {
		if (imageRegistry != null) {
			imageRegistry.dispose();
			imageRegistry = null;
			descriptors = null;
		}
	}
