		List<String> activeTag = new ArrayList<>();
		activeTag.add(EPartService.ACTIVE_ON_CLOSE_TAG);
		List<MPart> activeCandidates = modelService.findElements(perspective, null, MPart.class,
				activeTag);
		if (activeCandidates.size() > 0) {
			activeCandidates.get(0).getTags().remove(EPartService.ACTIVE_ON_CLOSE_TAG);
			MPart candidate = activeCandidates.get(0);
			if (partService.isInContainer(perspective, candidate)
					&& isValid(perspective, candidate)) {
				return candidate;
