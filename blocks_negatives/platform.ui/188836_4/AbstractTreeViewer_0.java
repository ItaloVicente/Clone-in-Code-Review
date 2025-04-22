				int insertionIndexInItems = indexInItems;
				while( insertionIndexInItems < items.length
						&& internalCompare(comparator, parentPath, element,
								items[insertionIndexInItems].getData()) == 0) {
					if (items[insertionIndexInItems].getData().equals(element)) {
						internalRefresh(element);
						continue elementloop;
