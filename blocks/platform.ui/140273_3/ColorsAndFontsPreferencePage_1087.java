			super.dispose();
			colorRegistry.removeListener(listener);
			fontRegistry.removeListener(listener);
			for (Iterator i = images.values().iterator(); i.hasNext();) {
				((Image) i.next()).dispose();
			}
			images.clear();

			if (emptyImage != null) {
				emptyImage.dispose();
				emptyImage = null;
			}

			clearFontCache();
		}

		public void clearFontCache() {
			for (Iterator i = fonts.values().iterator(); i.hasNext();) {
				((Font) i.next()).dispose();
			}
			fonts.clear();
		}

		public void clearFontCacheAndUpdate() {
			clearFontCache();
			fireLabelProviderChanged(new LabelProviderChangedEvent(PresentationLabelProvider.this));
		}

		@Override
