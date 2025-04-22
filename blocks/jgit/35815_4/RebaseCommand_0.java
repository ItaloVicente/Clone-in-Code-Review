
				if (!noVerify) {
					final ByteArrayOutputStream errorByteArray = new ByteArrayOutputStream();
					final PrintStream hookErrRedirect = new PrintStream(
							errorByteArray);

					final String upstreamName = Repository
							.shortenRefName(upstreamCommitName);

					ProcessResult preRebaseHookResult = FS.DETECTED
							.runIfPresent(repo
									new String[] { upstreamName
									hookErrRedirect
					final String errorDetails = errorByteArray.toString();
					if (preRebaseHookResult.getStatus() == ProcessResult.Status.OK
							&& preRebaseHookResult.getExitCode() != 0)
						rebaseRejectedByHook(Hook.PRE_REBASE
				}

