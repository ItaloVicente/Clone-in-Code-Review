			FS fs = repository.getFS();
			ProcessBuilder filterProcessBuilder = fs.runInShell(filterCommand,
					new String[0]);
			filterProcessBuilder.directory(repository.getWorkTree());
			filterProcessBuilder.environment().put(Constants.GIT_DIR_KEY,
					repository.getDirectory().getAbsolutePath());
			ExecutionResult result;
			try {
				result = fs.execute(filterProcessBuilder, in);
			} catch (IOException | InterruptedException e) {
				throw new IOException(new FilterFailedException(e,
						filterCommand, getEntryPathString()));
			}
			int rc = result.getRc();
			if (rc != 0) {
				throw new IOException(new FilterFailedException(rc,
						filterCommand, getEntryPathString(),
						result.getStdout().toByteArray(MAX_EXCEPTION_TEXT_SIZE),
						RawParseUtils.decode(result.getStderr()
								.toByteArray(MAX_EXCEPTION_TEXT_SIZE))));
