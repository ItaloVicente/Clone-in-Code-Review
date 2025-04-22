	synchronized PackBitmapIndex getBitmapIndex() throws IOException {
		if (bitmapIdx == null && hasExt(BITMAP_INDEX)) {
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

