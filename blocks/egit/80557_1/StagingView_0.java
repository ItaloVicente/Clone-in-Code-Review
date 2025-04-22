
		@Override
		public void addListener(ILabelProviderListener listener) {
			provider.addListener(listener);
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
			provider.removeListener(listener);
		}

		@Override
		public void dispose() {
			provider.dispose();
		}

		public ILabelProvider getLabelProvider() {
			return provider.getLabelProvider();
		}

