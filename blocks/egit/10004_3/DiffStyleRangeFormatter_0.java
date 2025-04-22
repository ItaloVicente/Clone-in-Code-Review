	@Override
	protected void writeHeadLine(ByteArrayOutputStream o, ChangeType type,
			String oldPath, String newPath)
			throws IOException {
		DiffStyleRange range = addRange(Type.HEADLINE);
		super.writeHeadLine(o, type, oldPath, newPath);
		stream.flushLine();
		range.length = stream.offset - range.start;
	}

