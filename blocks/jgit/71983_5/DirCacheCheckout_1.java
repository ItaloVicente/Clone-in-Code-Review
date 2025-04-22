		try (OutputStream channel = EolStreamTypeUtil.wrapOutputStream(
				new FileOutputStream(tmpFile)
			if (checkoutMetadata.smudgeFilterCommand != null) {
				if (Repository
						.isRegistered(checkoutMetadata.smudgeFilterCommand)) {
					BuiltinCommand command = Repository.getCommand(
							checkoutMetadata.smudgeFilterCommand
							ol.openStream()
					while (command.run() != -1)
						;
				} else {
					ProcessBuilder filterProcessBuilder = fs.runInShell(
							checkoutMetadata.smudgeFilterCommand
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
								checkoutMetadata.smudgeFilterCommand
								entry.getPathString()));
					}
					if (rc != 0) {
						throw new IOException(new FilterFailedException(rc
								checkoutMetadata.smudgeFilterCommand
								entry.getPathString()
								result.getStdout().toByteArray(MAX_EXCEPTION_TEXT_SIZE)
								RawParseUtils.decode(result.getStderr()
										.toByteArray(MAX_EXCEPTION_TEXT_SIZE))));
					}
