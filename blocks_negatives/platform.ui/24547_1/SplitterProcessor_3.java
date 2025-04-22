		List<MAddon> addons = app.getAddons();

		for (MAddon addon : addons) {
			if (addon.getContributionURI().contains(
				return;
		}
