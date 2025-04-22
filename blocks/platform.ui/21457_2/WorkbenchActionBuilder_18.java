	private boolean hasWelcomePage(AboutInfo[] infos) {
		for (int i = 0; i < infos.length; i++) {
			if (infos[i].getWelcomePageURL() != null) {
				return true;
			}
		}
		return false;
	}

	private boolean hasTipsAndTricks(AboutInfo[] infos) {
		for (int i = 0; i < infos.length; i++) {
			if (infos[i].getTipsAndTricksHref() != null) {
				return true;
			}
		}
		return false;
