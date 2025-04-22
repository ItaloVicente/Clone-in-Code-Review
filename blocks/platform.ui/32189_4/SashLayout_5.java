		boolean isHorizontal = sashContainer.isHorizontal();

		isValidating = true;
		SashUtil.validateContainerData(sashContainer,
				isHorizontal ? bounds.width : bounds.height, sashWidth);
		isValidating = false;
