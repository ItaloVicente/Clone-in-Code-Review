		boolean hasPlus = item.getItemCount() > 0;
		boolean needsPlus = category != null || entry.hasChildEntries();
		boolean removeAll = false;
		boolean addDummy = false;

		if (hasPlus != needsPlus) {
			if (needsPlus) {
				addDummy = true;
			} else {
				removeAll = true;
			}
		}
		if (removeAll) {
			TreeItem[] items = item.getItems();
			for (int i = 0; i < items.length; i++) {
				removeItem(items[i]);
			}
		}

		if (addDummy) {
			new TreeItem(item, SWT.NULL); // append a dummy to create the
		}
	}
