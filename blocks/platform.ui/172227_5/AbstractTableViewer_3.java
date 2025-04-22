		} else if (count != list.size()) {// As this is expensive skip it if all
			for (int i = 0; i < virtualManager.cachedElements.length; i++) {
				Object element = virtualManager.cachedElements[i];
				if (virtualElements.contains(element)) {
					Item item = doGetItem(i);
					item.getText();// Be sure to fire the update
					indices[count++] = i;
					virtualElements.remove(element);
					if (firstItem == null) {
						firstItem = item;
