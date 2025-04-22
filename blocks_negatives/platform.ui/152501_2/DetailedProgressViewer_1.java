		for (Control element : existingChildren) {
			((ProgressInfoItem) element).dispose();
		}

		int totalSize = Math.min(newItems.size(), MAX_DISPLAYED);

		for (int i = 0; i < totalSize; i++) {
			ProgressInfoItem item = createNewItem(infos[i]);
			item.setColor(i);
		}
