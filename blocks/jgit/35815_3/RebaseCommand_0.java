
				if (!noVerify) {
					final ByteArrayOutputStream errorByteArray = new ByteArrayOutputStream();
					final PrintStream hookErrRedirect = new PrintStream(
							errorByteArray);

					final String upstreamName = Repository
							.shortenRefName(upstreamCommitName);

					int preRebaseHookResult = FS.DETECTED.runIfPresent(repo
							Hook.PRE_REBASE
							System.out
					final String errorDetails = errorByteArray.toString();
					if (preRebaseHookResult != 0)
						rebaseRejectedByHook(Hook.PRE_REBASE
				}

