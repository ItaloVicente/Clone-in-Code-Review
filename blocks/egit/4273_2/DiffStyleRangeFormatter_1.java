
	@Override
	public void format(final EditList edits, final RawText a, final RawText b)
			throws IOException {
		stream.flushLine();
		super.format(edits, a, b);
	}
