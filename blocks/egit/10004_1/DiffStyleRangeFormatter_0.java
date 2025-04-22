	@Override
	protected void writeHeadLine(ChangeType type, String oldPath, String newPath)
			throws IOException {
		DiffStyleRange range = addRange(Type.HEADLINE);
		super.writeHeadLine(type, oldPath, newPath);
		stream.flushLine();
		range.length = stream.offset - range.start;
	}

