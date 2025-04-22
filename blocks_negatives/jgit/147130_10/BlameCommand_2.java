
	private RawText getRawText(File inTree) throws IOException,
			FileNotFoundException {
		RawText rawText;

		WorkingTreeOptions workingTreeOptions = getRepository().getConfig()
				.get(WorkingTreeOptions.KEY);
		AutoCRLF autoCRLF = workingTreeOptions.getAutoCRLF();
		switch (autoCRLF) {
		case FALSE:
		case INPUT:
			rawText = new RawText(inTree);
			break;
		case TRUE:
			try (AutoLFInputStream in = new AutoLFInputStream(
					new FileInputStream(inTree), true)) {
				rawText = new RawText(toByteArray(in, (int) inTree.length()));
			}
			break;
		default:
			throw new IllegalArgumentException(
		}
		return rawText;
	}

	private static byte[] toByteArray(InputStream source, int upperSizeLimit)
			throws IOException {
		byte[] buffer = new byte[upperSizeLimit];
		try {
			int read = IO.readFully(source, buffer, 0);
			if (read == upperSizeLimit) {
				return buffer;
			}
			byte[] copy = new byte[read];
			System.arraycopy(buffer, 0, copy, 0, read);
			return copy;
		} finally {
			source.close();
		}
	}
