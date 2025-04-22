	private MPerspective getPerspectiveIfOpen(IPerspectiveDescriptor perspToOverwrite) {
		MPerspective perspective = null;
		List<MPerspective> foundPerspectives = modelService.findElements(application, perspToOverwrite.getId(), MPerspective.class, null);
		if (!foundPerspectives.isEmpty()) {
			perspective = foundPerspectives.get(0);
		}
		return perspective;
	}

