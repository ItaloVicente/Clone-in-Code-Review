	protected DiffStyleRange addRange(Type type, int start, int end) {
		DiffStyleRange range = new DiffStyleRange();
		range.start = start;
		range.diffType = type;
		range.length = end - start;
		ranges.add(range);
