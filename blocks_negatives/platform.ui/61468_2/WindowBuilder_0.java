		if (part == null) {
			part = modelService.createModelElement(MPart.class);
			part.setElementId(id);
			part.setContributionURI(CompatibilityPart.COMPATIBILITY_VIEW_URI);
			part.getTags().add(ViewRegistry.VIEW_TAG);
			sharedElements.add(part);
		}
