	public static String getSimpleFetchCommandLabel(RemoteConfig config) {
		String target = UIText.SimpleConfigureFetchDialog_UpstreamSource;
		if (config != null) {
			target = config.getName();
		}
		return NLS.bind(UIText.SimpleConfigureFetchDialog_FetchFromLabel,
				target);
	}

