
		final List<MToolBar> children = modelService.findElements(window, null, MToolBar.class, null);
		for (MToolBar el : children) {
			if (!el.getTags().contains(TOOLBAR_SEPARATOR) && !el.getTags().contains(IPresentationEngine.NO_MOVE)) {
				el.getTags().add(IPresentationEngine.NO_MOVE);
			}
		}

