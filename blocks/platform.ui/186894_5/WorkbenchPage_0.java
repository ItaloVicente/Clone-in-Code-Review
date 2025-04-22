		MPerspective mPerspective = getPerspectiveStack().getSelectedElement();
		List<String> ids = ModeledPageLayout.getIds(mPerspective, ModeledPageLayout.SHOW_IN_PART_TAG);
		IPerspectiveDescriptor perspective = getPerspective();
		if (perspective != null && perspective.getDefaultShowIn() != null) {
			ids = new ArrayList<>(ids);
			if (ids.remove(perspective.getDefaultShowIn())) {
				ids.add(0, perspective.getDefaultShowIn());
