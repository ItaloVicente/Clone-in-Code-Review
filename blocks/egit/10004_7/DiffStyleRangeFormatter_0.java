	@Override
	protected void formatGitDiffFirstHeaderLine(ByteArrayOutputStream o,
			final ChangeType type, final String oldPath, final String newPath)
			throws IOException {
		stream.flushLine();
		int offset = stream.offset;
		int start = o.size();
		super.formatGitDiffFirstHeaderLine(o, type, oldPath, newPath);
		int end = o.size();
		addRange(Type.HEADLINE, offset + start, offset + end);
	}

