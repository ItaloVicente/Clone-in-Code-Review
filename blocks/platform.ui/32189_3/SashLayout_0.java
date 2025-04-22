	protected void normalizeRelativeWeights() {
		isValidating = true;
		double availableRelative;
		List<MUIElement> visibleChildren = SashUtil
				.getVisibleChildren((MGenericTile<?>) root);
		availableRelative = SashUtil.getTotalWeight(visibleChildren);
		for (MUIElement ele : visibleChildren) {
			SizeInfo i2 = SizeInfo.parse(ele.getContainerData());
			if (!i2.isDefaultAbsolute()) {
				double weight = i2.getDefaultValue() * 100.0
						/ availableRelative;
				i2.setDefaultValue(weight);
				ele.setContainerData(i2.getEncodedParameters());
			}
		}
		isValidating = false;
	}

