			double newSize;

			SizeInfo sizeInfo = SizeInfo.parse(subNode.getContainerData());
			newSize = sizeInfo.getValueConstrained(totalWeight,
					availableRelative);
			sizeInfo.setDefaultValue(newSize);

			double newPx = sizeInfo.getValueAsAbsolute(totalWeight,
					availableRelative);
