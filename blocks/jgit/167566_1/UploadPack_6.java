
	private static boolean isSideband(FetchRequest req) {
		Set<String> caps = req.getClientCapabilities();
		return caps.contains(OPTION_SIDE_BAND)
				|| caps.contains(OPTION_SIDE_BAND_64K);
	}

