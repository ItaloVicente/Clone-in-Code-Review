	private boolean hasWelcomePage(AboutInfo[] infos) {
		for (AboutInfo info : infos) {
			if (info.getWelcomePageURL() != null) {
				return true;
			}
		}
		return false;
	}

	private boolean hasTipsAndTricks(AboutInfo[] infos) {
		for (AboutInfo info : infos) {
			if (info.getTipsAndTricksHref() != null) {
				return true;
			}
		}
		return false;
