	private InputStream filterClean(InputStream in) throws IOException {
		in = handleAutoCRLF(in);
		String filterCommand = getCleanFilterCommand();
		if (filterCommand != null) {
			FS fs = repository.getFS();
			ProcessBuilder filterProcessBuilder = fs.runInShell(filterCommand
					new String[0]);

			ExecutionResult result;
			try {
				result = fs.execute(filterProcessBuilder
			} catch (IOException | InterruptedException e) {
				throw new IOException(new FilterFailedException(e
						filterCommand
			}
			int rc = result.getRc();
			if (rc != 0) {
				throw new IOException(new FilterFailedException(rc
						filterCommand
						result.getStdout().toByteArray(MAX_EXCEPTION_TEXT_SIZE)
						RawParseUtils.decode(result.getStderr()
								.toByteArray(MAX_EXCEPTION_TEXT_SIZE))));
			}
			return result.getStdout().openInputStream();
		}
		return in;
	}

	private InputStream handleAutoCRLF(InputStream in) {
		AutoCRLF autoCRLF = getOptions().getAutoCRLF();
		if (autoCRLF == AutoCRLF.TRUE || autoCRLF == AutoCRLF.INPUT) {
			in = new EolCanonicalizingInputStream(in
		}
		return in;
