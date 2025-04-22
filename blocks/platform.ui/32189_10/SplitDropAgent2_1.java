		if (hasSizeData != null) {
			PartSizeInfo newSizeInfo = PartSizeInfo.get(toInsert);
			newSizeInfo.setResizeMode(PartSizeInfo.get(hasSizeData).getResizeMode());
			newSizeInfo.storeInfo();
			newSizeInfo.notifyChanged();
		} else {
			PartSizeInfo newSizeInfo = PartSizeInfo.get(toInsert);
			newSizeInfo.setResizeMode(PartSizeInfo.get(originalParent).getResizeMode());
			newSizeInfo.storeInfo();
			newSizeInfo.notifyChanged();
		}
