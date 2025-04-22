		EPartService partService = (EPartService) window.getContext().get(
				EPartService.class.getName());
		MPlaceholder placeholderA = partService
				.createSharedPart("partId", true);
		MPlaceholder placeholderB = partService
				.createSharedPart("partId", true);
