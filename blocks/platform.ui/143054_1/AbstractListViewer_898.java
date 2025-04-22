		int count = listGetItemCount();
		int min = 0, max = count - 1;
		while (min <= max) {
			int mid = (min + max) / 2;
			Object data = listMap.get(mid);
			int compare = comparator.compare(this, data, element);
			if (compare == 0) {
				while (compare == 0) {
					++mid;
					if (mid >= count) {
						break;
					}
					data = listMap.get(mid);
					compare = comparator.compare(this, data, element);
				}
				return mid;
			}
			if (compare < 0) {
