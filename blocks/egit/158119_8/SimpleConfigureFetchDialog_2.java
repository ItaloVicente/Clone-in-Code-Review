	public static String getSimpleFetchCommandLabel(
			@NonNull RemoteConfig config) {
		String target = config.getName();
		return NLS.bind(UIText.SimpleConfigureFetchDialog_FetchFromLabel,
				target);
	}

