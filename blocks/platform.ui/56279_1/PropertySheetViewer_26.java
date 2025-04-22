		}

		if (widget == tree && oldCnt == 0 && tree.getItemCount() == 1) {
			tree.setRedraw(false);
			tree.setRedraw(true);
		}

		childItems = getChildItems(widget);

		for (int i = 0; i < newSize; i++) {
			Object el = children.get(i);
			if (el instanceof IPropertySheetEntry) {
