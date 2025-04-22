	public IColorAndFontProvider getColorAndFontProvider() {
		if (colorAndFontProviderTracker == null) {
			if (context == null) {
				return null;
			}
			colorAndFontProviderTracker = new ServiceTracker(context,
					IColorAndFontProvider.class.getName(), null);
			colorAndFontProviderTracker.open();
		}
		return (IColorAndFontProvider) colorAndFontProviderTracker.getService();
	}
