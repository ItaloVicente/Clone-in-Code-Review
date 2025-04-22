	private void compare(List<DiffEntry> files) throws IOException {
		ContentSource.Pair sourcePair = new ContentSource.Pair(source(oldTree)
				source(newTree));
		try {
			for (int fileIndex = 0; fileIndex < files.size(); fileIndex++) {
				DiffEntry ent = files.get(fileIndex);

				String filePath = ent.getNewPath();
				if (filePath.equals(DiffEntry.DEV_NULL)) {
					filePath = ent.getOldPath();
				}

				try {
					FileElement local = createFileElement(
							FileElement.Type.LOCAL
					FileElement remote = createFileElement(
							FileElement.Type.REMOTE

					PromptContinueHandler promptContinueHandler = new CountingPromptContinueHandler(
							fileIndex + 1

					Optional<ExecutionResult> optionalResult = diffTools
							.compare(local
									trustExitCode
									this::informUserNoTool);

					if (optionalResult.isPresent()) {
						ExecutionResult result = optionalResult.get();
						Charset defaultCharset = SystemReader.getInstance()
								.getDefaultCharset();
						outw.println(
								new String(result.getStdout().toByteArray()
										defaultCharset));
						outw.flush();
						errw.println(
								new String(result.getStderr().toByteArray()
										defaultCharset));
						errw.flush();
					}
				} catch (ToolException e) {
					outw.println(e.getResultStdout());
					outw.flush();
					errw.println(e.getMessage());
					errw.flush();
					throw die(MessageFormat.format(
							CLIText.get().diffToolDied
				}
