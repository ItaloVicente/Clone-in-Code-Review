		for (MAddon addon : addons) {
			if (addon.getContributionURI()
				return;
		}

		MAddon cleanupAddon = modelService.createModelElement(MAddon.class);
		cleanupAddon
		app.getAddons().add(cleanupAddon);
