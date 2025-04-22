	protected DiffRegion addRegion(@NonNull Type type, int start, int end,
			int aLine, int bLine) {
		maximumLineNumbers[0] = Math.max(aLine, maximumLineNumbers[0]);
		maximumLineNumbers[1] = Math.max(bLine, maximumLineNumbers[1]);
		if (bLine != DiffRegion.NO_LINE) {
			lastNewLine = bLine;
		}
