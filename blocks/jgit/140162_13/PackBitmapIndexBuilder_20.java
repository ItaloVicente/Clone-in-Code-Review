			@Override
			public StoredEntry next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				StoredBitmap item = byAddOrder.get(index);
				int bestXorOffset = 0;
				EWAHCompressedBitmap bestBitmap = item.getBitmap();

				for (int i = 1; i <= MAX_XOR_OFFSET_SEARCH; i++) {
					int curr = i + index;
					if (curr >= byAddOrder.size()) {
						break;
