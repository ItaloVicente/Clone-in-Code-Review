
				if (commitSuccessful) {
					final ByteArrayOutputStream errorByteArray = new ByteArrayOutputStream();
					final PrintStream hookErrRedirect = new PrintStream(
							errorByteArray);
					int postCommitHookResult = FS.DETECTED.runIfPresent(repo
							Hook.POST_COMMIT
							hookErrRedirect);
					final String errorDetails = errorByteArray.toString();
					if (postCommitHookResult != 0) {
					}
				}
