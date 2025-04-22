	protected void writeHeadLine(final ChangeType type
			final String newPath) throws IOException {
		out.write(encode(quotePath(oldPrefix
				+ (type == ADD ? newPath : oldPath))));
		out.write(' ');
		out.write(encode(quotePath(newPrefix
				+ (type == DELETE ? oldPath : newPath))));
		out.write('\n');
	}

