	private static void safeClose(final InputStream in) {
		try {
			in.close();
		} catch (IOException err2) {
		}
	}

	private boolean mightNeedCleaning(Entry entry) {
		return options.isAutoCRLF();
	}

	private boolean isBinary(Entry entry
		return RawText.isBinary(content
	}

	private boolean isBinary(Entry entry) throws IOException {
		InputStream in = entry.openInputStream();
		try {
			return RawText.isBinary(in);
		} finally {
			safeClose(in);
		}
	}

	private ByteBuffer filterClean(Entry entry
			throws IOException {
		InputStream in = new ByteArrayInputStream(src);
		return IO.readWholeStream(filterClean(entry
	}

	private InputStream filterClean(Entry entry
		return new EolCanonicalizingInputStream(in);
	}

	public WorkingTreeOptions getOptions() {
		return options;
	}

