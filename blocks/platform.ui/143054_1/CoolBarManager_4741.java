			CoolItem coolItem = findCoolItem(coolItems, item);
			if (item.isSeparator()) {
				foundSeparator = true;
			}
			if ((!item.isSeparator()) && (!item.isGroupMarker()) && (isChildVisible(item)) && (coolItem != null)
					&& (foundSeparator)) {
				wrapIndices[j] = coolBar.indexOf(coolItem);
				j++;
				foundSeparator = false;
			}
		}

		final int[] oldIndices = coolBar.getWrapIndices();
		boolean shouldUpdate = false;
		if (oldIndices.length == wrapIndices.length) {
			for (int i = 0; i < oldIndices.length; i++) {
				if (oldIndices[i] != wrapIndices[i]) {
					shouldUpdate = true;
					break;
				}
			}
		} else {
			shouldUpdate = true;
		}

		if (shouldUpdate) {
			coolBar.setWrapIndices(wrapIndices);
		}
	}
