		boolean isHorizontal = sashContainer.isHorizontal();

		isValidating = true;
		SashUtil.validatePartSize(sashContainer, isHorizontal ? bounds.width
				: bounds.height, sashWidth);
		isValidating = false;

		List<MUIElement> visibleChildren = SashUtil
				.getVisibleChildren(sashContainer);
