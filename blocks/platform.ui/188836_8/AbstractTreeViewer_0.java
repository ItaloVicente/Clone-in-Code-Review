				int insertionIndexInItems = indexInItems - 1;
				int directionStep = -1;
				while (insertionIndexInItems < items.length) {
					if (insertionIndexInItems >= 0 && internalCompare(comparator, parentPath, element,
							items[insertionIndexInItems].getData()) == 0) {
						if (items[insertionIndexInItems].getData().equals(element)) {
							internalRefresh(element);
							continue elementloop;
						}
						insertionIndexInItems += directionStep;
					} else {
						if (directionStep < 0) {
							directionStep = 1;
							insertionIndexInItems = indexInItems;
						} else {
							break;
						}
