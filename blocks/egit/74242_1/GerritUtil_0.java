
	public static boolean isGerritFetch(RemoteConfig rc) {
		for (RefSpec fetchSpec : rc.getFetchRefSpecs()) {
			String source = fetchSpec.getSource();
			String destination = fetchSpec.getDestination();
			if (source == null || destination == null) {
				continue;
			}
			if (source.startsWith(Constants.R_NOTES)
					&& destination.startsWith(Constants.R_NOTES)) {
				return true;
			}
		}
		return false;
	}
