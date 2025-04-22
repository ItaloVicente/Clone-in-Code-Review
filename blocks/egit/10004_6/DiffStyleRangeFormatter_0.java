	@Override
	protected void formatGitDiffFirstHeaderLine(ByteArrayOutputStream o,
			final ChangeType type, final String oldPath, final String newPath)
			throws IOException {
		int start = o.size();
		super.formatGitDiffFirstHeaderLine(o, type, oldPath, newPath);
		addRange(Type.HEADLINE, start, o.size());
	}

