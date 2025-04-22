		double totalWeight = SashUtil.getTotalWeight(visibleChildren);
		double availableRelative = SashUtil.getAvailableRelative(isHorizontal,
				isHorizontal ? bounds.width : bounds.height, sashWidth,
				visibleChildren);

		int tilePos = isHorizontal ? bounds.x : bounds.y;
