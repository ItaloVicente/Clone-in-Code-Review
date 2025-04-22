	synchronized PackBitmapIndex getBitmapIndex() throws IOException {
		if (extensions.contains(BITMAP_INDEX) && bitmapIdx == null) {
			final PackBitmapIndex idx = PackBitmapIndex.open(
					extFile(BITMAP_INDEX)

			if (packChecksum == null)
				packChecksum = idx.packChecksum;
			else if (!Arrays.equals(packChecksum
				throw new PackMismatchException(
						JGitText.get().packChecksumMismatch);

			bitmapIdx = idx;
		}
		return bitmapIdx;
	}

