	private List<MAddon> getObsoleteAddons(List<MAddon> addons) {
		for (Iterator<MAddon> iterator = addons.iterator(); iterator.hasNext();) {
			MContribution appElement = iterator.next();
			boolean validAppElement = isValidContribution(appElement);
			if (validAppElement) {
				iterator.remove();
			} else {
				logMissingClassWarning(appElement);
			}
		}

		return addons;
	}

