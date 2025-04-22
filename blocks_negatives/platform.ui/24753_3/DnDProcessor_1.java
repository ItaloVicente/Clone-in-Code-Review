		for (MAddon addon : addons) {
				return;
		}

		MAddon dndAddon = modelService.createModelElement(MAddon.class);
		app.getAddons().add(dndAddon);
