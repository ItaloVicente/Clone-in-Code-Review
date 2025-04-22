		for (Control child : existingChildren) {
			((ProgressInfoItem) child).dispose();
		}

		int totalSize = Math.min(newItems.size(), getMaxDisplayed());

		for (int i = 0; i < totalSize; i++) {
			ProgressInfoItem item = createNewItem(infos[i]);
			item.setColor(i);
		}
