
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
