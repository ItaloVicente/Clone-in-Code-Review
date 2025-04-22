	private MergeResult<RawText> binaryMergeResult() {
		MergeResult<RawText> r = new MergeResult<>(Collections.<RawText>emptyList());
		r.setContainsConflicts(true);
		return r;
	}

