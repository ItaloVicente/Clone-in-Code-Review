	@Override
	protected void formatGitDiffFirstHeaderLine(ByteArrayOutputStream o,
			final ChangeType type, final String oldPath, final String newPath)
			throws IOException {
		DiffStyleRange range = addRange(Type.HEADLINE);
		super.formatGitDiffFirstHeaderLine(o, type, oldPath, newPath);
		stream.flushLine();
		range.length = stream.offset - range.start;
	}

