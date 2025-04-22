
				if (!noVerify) {
					final ByteArrayOutputStream errorByteArray = new ByteArrayOutputStream();
					final PrintStream hookErrRedirect = new PrintStream(
							errorByteArray);

					final String upstreamName = Repository
							.shortenRefName(upstreamCommitName);

					ProcessResult preRebaseHookResult = FS.DETECTED
							.runIfPresent(repo
									new String[] { upstreamName
									hookOutRedirect
					if (preRebaseHookResult.getStatus() == ProcessResult.Status.OK
							&& preRebaseHookResult.getExitCode() != 0) {
						String errorMessage = MessageFormat.format(
								JGitText.get().rebaseRejectedByHook
								Hook.PRE_REBASE.getName()
								errorByteArray.toString());
						throw new RejectRebaseException(errorMessage);
					}
				}

