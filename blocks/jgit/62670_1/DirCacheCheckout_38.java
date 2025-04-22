		if (smudgeFilterCommand != null) {
			ProcessBuilder filterProcessBuilder = fs
					.runInShell(smudgeFilterCommand
			filterProcessBuilder.directory(repo.getWorkTree());
			filterProcessBuilder.environment().put(Constants.GIT_DIR_KEY
					repo.getDirectory().getAbsolutePath());
			ExecutionResult result;
			int rc;
			try {
				result = fs.execute(filterProcessBuilder
				rc = result.getRc();
				if (rc == 0) {
					result.getStdout().writeTo(channel
							NullProgressMonitor.INSTANCE);
				}
			} catch (IOException | InterruptedException e) {
				throw new IOException(new FilterFailedException(e
						smudgeFilterCommand

			} finally {
				channel.close();
			}
			if (rc != 0) {
				throw new IOException(new FilterFailedException(rc
						smudgeFilterCommand
						result.getStdout().toByteArray(MAX_EXCEPTION_TEXT_SIZE)
						RawParseUtils.decode(result.getStderr()
								.toByteArray(MAX_EXCEPTION_TEXT_SIZE))));
			}
		} else {
			try {
				ol.copyTo(channel);
			} finally {
				channel.close();
			}
		}
		if (opt.getAutoCRLF() == AutoCRLF.TRUE || smudgeFilterCommand != null) {
			entry.setLength(tmpFile.length());
		} else {
			entry.setLength(ol.getSize());
