
	private RawText getRawText(File inTree) throws IOException
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
			EolCanonicalizingInputStream in = new EolCanonicalizingInputStream(
					new FileInputStream(inTree)
			rawText = new RawText(toByteArray(in
			break;
		default:
			throw new IllegalArgumentException(
		}
		return rawText;
	}

	private static byte[] toByteArray(InputStream source
			throws IOException {
		byte[] buffer = new byte[upperSizeLimit];
		try {
			int read = IO.readFully(source
			if (read == upperSizeLimit)
				return buffer;
			else {
				byte[] copy = new byte[read];
				System.arraycopy(buffer
				return copy;
			}
		} finally {
			source.close();
		}
	}
