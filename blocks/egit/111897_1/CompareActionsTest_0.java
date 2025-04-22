	private static void setEnabledModelProvider(String modelProviderId) {
		ITeamContentProviderManager contentProviderManager = TeamUI
				.getTeamContentProviderManager();
		ITeamContentProviderDescriptor descriptor = contentProviderManager
				.getDescriptor(modelProviderId);
		contentProviderManager.setEnabledDescriptors(
				new ITeamContentProviderDescriptor[] { descriptor });
	}

