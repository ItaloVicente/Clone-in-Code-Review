		Collection<MPart> candidates = perspective.getContext().get(EPartService.class).getParts();
		MPart activeOnClose = candidates.stream()
				.filter(part -> part.getTags().contains(EPartService.ACTIVE_ON_CLOSE_TAG))
				.findAny().orElse(null);
		if (activeOnClose != null) {
			activeOnClose.getTags().remove(EPartService.ACTIVE_ON_CLOSE_TAG);
			if (isValid(activeOnClose)) {
				return activeOnClose;
