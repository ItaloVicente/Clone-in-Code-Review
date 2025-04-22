	public Iterable<StoredEntry> getCompressedBitmaps() {
		return () -> new Iterator<StoredEntry>() {

			private int index = byAddOrder.size() - 1;

			@Override
			public boolean hasNext() {
				return index >= 0;
			}

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
					}

					StoredBitmap other = byAddOrder.get(curr);
					EWAHCompressedBitmap bitmap = other.getBitmap()
							.xor(item.getBitmap());

					if (bitmap.sizeInBytes() < bestBitmap.sizeInBytes()) {
						bestBitmap = bitmap;
						bestXorOffset = i;
					}
				}
				index--;

				PositionEntry entry = positionEntries.get(item);
				if (entry == null) {
					throw new IllegalStateException();
				}
				bestBitmap.trim();
				return new StoredEntry(entry.namePosition, bestBitmap,
						bestXorOffset, item.getFlags());
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
