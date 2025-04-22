	public static boolean isGerritRemote(RemoteConfig rc) {
		for (RefSpec pushSpec : rc.getPushRefSpecs()) {
			String destination = pushSpec.getDestination();
			if (destination == null) {
				continue;
			}
			if (destination.startsWith(GerritUtil.REFS_FOR)
					|| destination.startsWith(GerritUtil.REFS_PUBLISH)
					|| destination.startsWith(GerritUtil.REFS_DRAFTS)) {
				return true;
			}
		}
		return false;
	}
