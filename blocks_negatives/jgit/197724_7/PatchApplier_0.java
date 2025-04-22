	private boolean isNoNewlineAtEndOfFile(FileHeader fh) {
		List<? extends HunkHeader> hunks = fh.getHunks();
		if (hunks == null || hunks.isEmpty()) {
			return false;
		}
		HunkHeader lastHunk = hunks.get(hunks.size() - 1);
		byte[] buf = new byte[lastHunk.getEndOffset()
				- lastHunk.getStartOffset()];
		System.arraycopy(lastHunk.getBuffer(), lastHunk.getStartOffset(), buf,
				0, buf.length);
		RawText lhrt = new RawText(buf);
		return lhrt.getString(lhrt.size() - 1)
