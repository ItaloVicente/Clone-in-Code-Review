				int insertionIndexInItems = indexInItems - 1;
				while (insertionIndexInItems >= 0 && internalCompare(comparator, parentPath, element,
						items[insertionIndexInItems].getData()) == 0) {
					if (items[insertionIndexInItems].getData().equals(element)) {
						internalRefresh(element);
						continue elementloop;
					}
					insertionIndexInItems--;
				}
				insertionIndexInItems = indexInItems;
