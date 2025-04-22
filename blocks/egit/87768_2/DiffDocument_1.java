	int getLogicalLine(int physicalLine, @NonNull DiffEntry.Side side) {
		int offset;
		try {
			offset = getLineOffset(physicalLine);
			DiffRegion region = findRegion(offset);
			if (region == null) {
				return DiffRegion.NO_LINE;
			}
			int logicalStart = region.getLine(side);
			if (logicalStart == DiffRegion.NO_LINE) {
				return DiffRegion.NO_LINE;
			}
			int physicalStart = getLineOfOffset(region.getOffset());
			return logicalStart + (physicalLine - physicalStart);
		} catch (BadLocationException e) {
			return DiffRegion.NO_LINE;
		}
	}

	Pattern getPathPattern(@NonNull DiffEntry.Side side) {
