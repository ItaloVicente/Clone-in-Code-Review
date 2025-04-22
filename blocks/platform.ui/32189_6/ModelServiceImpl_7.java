		PartSizeInfo info = PartSizeInfo.parse(toInsert.getContainerData());
		List<MUIElement> viskids = SashUtil.getVisibleChildren(newSash);
		double weight = SashUtil.getTotalWeight(viskids) / viskids.size();
		info.setDefaultAbsolute(false);
		info.setDefaultValue(weight);
		toInsert.setContainerData(info.getEncodedParameters());
