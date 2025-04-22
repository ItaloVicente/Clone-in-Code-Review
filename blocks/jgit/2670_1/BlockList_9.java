	public void setSize(int newSize) {
		if (size < newSize) {
			do {
				add(null);
			} while (size < newSize);

		} else if (newSize < size) {
			if (newSize == 0) {
				clear();
				return;
			}
			do {
				remove(size - 1);
			} while (newSize < size);
		}
	}

