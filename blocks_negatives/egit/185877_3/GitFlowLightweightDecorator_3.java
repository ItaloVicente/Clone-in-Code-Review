
		private void decorateIcons(IDecoration decoration,
				GitFlowConfig config) {
			ImageDescriptor overlay = null;

			if (config.isInitialized()) {
				overlay = INITIALIZED_IMAGE;
			}


			if (overlay != null) {
				decoration.addOverlay(overlay);
			}
		}

