	private InputStream filterClean(InputStream in) throws IOException {
		AutoCRLF autoCRLF = getOptions().getAutoCRLF();
		if (autoCRLF == AutoCRLF.TRUE || autoCRLF == AutoCRLF.INPUT) {
			in = new EolCanonicalizingInputStream(in
		}
		String filterCommand = getCleanFilterCommand();
		if (filterCommand != null) {
			TemporaryBuffer attributeProcessedContent = null;
			try {
				attributeProcessedContent = processFilter(filterCommand
						getEntryPathString()
			} catch (FilterFailedException e) {
				throw new IOException(e);
			}
			if (attributeProcessedContent != null)
				in = attributeProcessedContent.openInputStream();
		}
		return in;
