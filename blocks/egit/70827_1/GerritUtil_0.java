	public static boolean isGerritRemote(RemoteConfig rc) {
		for (RefSpec pushSpec : rc.getPushRefSpecs()) {
			String destination = pushSpec.getDestination();
			if (destination == null) {
				continue;
			}
			if (destination.startsWith(GerritUtil.REFS_FOR)) {
				return true;
			}
		}
		return false;
	}
