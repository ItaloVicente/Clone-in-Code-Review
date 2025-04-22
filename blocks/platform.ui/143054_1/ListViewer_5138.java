		int height = list.getItemHeight();
		Rectangle rect = list.getClientArea();
		int topIndex = list.getTopIndex();
		int visibleCount = Math.max(rect.height / height, 1);
		int bottomIndex = Math.min(topIndex + visibleCount, count) - 1;
		if ((topIndex <= index) && (index <= bottomIndex)) {
