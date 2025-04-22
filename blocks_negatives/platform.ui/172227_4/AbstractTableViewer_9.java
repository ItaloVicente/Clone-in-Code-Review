		} else {

				for (int i = 0; i < virtualManager.cachedElements.length; i++) {
					Object element = virtualManager.cachedElements[i];
					if (virtualElements.contains(element)) {
						Item item = doGetItem(i);
						indices[count++] = i;
						virtualElements.remove(element);
						if (firstItem == null) {
							firstItem = item;
						}
