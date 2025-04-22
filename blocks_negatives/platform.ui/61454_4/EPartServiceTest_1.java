		EPartService partService = (EPartService) window.getContext().get(
				EPartService.class.getName());
		MPlaceholder placeholderA = partService.createSharedPart("partId",
				false);
		MPlaceholder placeholderB = partService.createSharedPart("partId",
				false);
