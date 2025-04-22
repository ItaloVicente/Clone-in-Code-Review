		if (dropIndex == -1) {
			if (inCenter) {
				dropIndex = itemRects == null ? 0 : itemRects.size() - 1;
			} else {
				System.out.println("track returning true - no tabs under cursor");
				return true;
			}
		}
		if (curDropIndex == dropIndex)
