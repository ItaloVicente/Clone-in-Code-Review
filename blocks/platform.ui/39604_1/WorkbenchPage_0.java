		Collection<MPart> partsToHide = partService.getParts();
		List<MPart> partsOutsidePersp = modelService.findElements(window, null, MPart.class, null,
				EModelService.OUTSIDE_PERSPECTIVE);
		partsToHide.removeAll(partsOutsidePersp);

		for (MPart part : partsToHide) {
