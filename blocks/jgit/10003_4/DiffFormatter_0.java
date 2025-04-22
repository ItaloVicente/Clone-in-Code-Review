	protected void formatGitDiffFirstHeaderLine(ByteArrayOutputStream o
			final ChangeType type
			throws IOException {
		o.write(encode(quotePath(oldPrefix + (type == ADD ? newPath : oldPath))));
		o.write(' ');
		o.write(encode(quotePath(newPrefix
				+ (type == DELETE ? oldPath : newPath))));
		o.write('\n');
	}

