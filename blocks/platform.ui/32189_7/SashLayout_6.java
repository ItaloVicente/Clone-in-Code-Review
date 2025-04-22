			PartSizeInfo sizeInfo = PartSizeInfo.parse(subNode
					.getContainerData());
			newSize = sizeInfo.getValueConstrained(totalWeight,
					availableRelative);
			sizeInfo.setDefaultValue(newSize);

			double newPx = sizeInfo.getValueAsAbsolute(totalWeight,
					availableRelative);

			int rndSize = (int) (newPx + .5);
