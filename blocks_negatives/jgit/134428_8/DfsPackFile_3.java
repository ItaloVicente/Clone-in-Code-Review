				throw new IOException(MessageFormat.format(
						DfsText.get().cannotReadIndex,
						desc.getFileName(INDEX)), e);
			}

			setPackIndex(idx);
			return idx;
		}
	}

	final boolean isGarbage() {
		return desc.getPackSource() == UNREACHABLE_GARBAGE;
	}

	PackBitmapIndex getBitmapIndex(DfsReader ctx) throws IOException {
		if (invalid || isGarbage() || !desc.hasFileExt(BITMAP_INDEX))
			return null;

		DfsBlockCache.Ref<PackBitmapIndex> idxref = bitmapIndex;
		if (idxref != null) {
			PackBitmapIndex idx = idxref.get();
			if (idx != null)
				return idx;
		}

		synchronized (initLock) {
			idxref = bitmapIndex;
			if (idxref != null) {
				PackBitmapIndex idx = idxref.get();
				if (idx != null)
					return idx;
