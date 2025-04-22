				ProcessBuilder filterProcessBuilder = fs.runInShell(
						checkoutMetadata.smudgeFilterCommand, new String[0]);
				filterProcessBuilder.directory(repo.getWorkTree());
				filterProcessBuilder.environment().put(Constants.GIT_DIR_KEY,
						repo.getDirectory().getAbsolutePath());
				ExecutionResult result;
				int rc;
				try {
					result = fs.execute(filterProcessBuilder, ol.openStream());
					rc = result.getRc();
					if (rc == 0) {
						result.getStdout().writeTo(channel,
								NullProgressMonitor.INSTANCE);
					}
				} catch (IOException | InterruptedException e) {
					throw new IOException(new FilterFailedException(e,
