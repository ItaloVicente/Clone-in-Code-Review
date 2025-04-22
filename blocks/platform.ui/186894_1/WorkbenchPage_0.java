		MPerspective mPerspective = getPerspectiveStack().getSelectedElement();
		List<String> ids = ModeledPageLayout.getIds(mPerspective, ModeledPageLayout.SHOW_IN_PART_TAG);
		IPerspectiveDescriptor perspective = getPerspective();
		if (perspective != null && perspective.getShowContainerIn() != null) {
			ids = new ArrayList<>(ids);
			ids.remove(perspective.getShowContainerIn());
			ids.add(0, perspective.getShowContainerIn());
