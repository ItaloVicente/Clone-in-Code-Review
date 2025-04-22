	public static String getSimplePushCommandLabel(
			@NonNull RemoteConfig config) {
		String target = config.getName();
		return NLS.bind(UIText.SimpleConfigurePushDialog_PushToLabel, target);
	}

