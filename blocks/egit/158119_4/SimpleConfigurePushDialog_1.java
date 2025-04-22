	public static String getSimplePushCommandLabel(RemoteConfig config) {
		String target = UIText.SimpleConfigurePushDialog_UpstreamTarget;
		if (config != null) {
			target = config.getName();
		}
		return NLS.bind(UIText.SimpleConfigurePushDialog_PushToLabel, target);
	}

