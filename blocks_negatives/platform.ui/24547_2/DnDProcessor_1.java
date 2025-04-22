		List<MAddon> addons = app.getAddons();

		for (MAddon addon : addons) {
				return;
		}

		MAddon dndAddon = modelService.createModelElement(MAddon.class);
		app.getAddons().add(dndAddon);
