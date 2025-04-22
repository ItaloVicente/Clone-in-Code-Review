			if (amend && headId != null && !noPostRewrite) {
				final ObjectId oldId = headId;
				final ObjectId newId = revCommit.getId();
				final String rewritten = oldId.getName() + ' '
						+ newId.getName() + '\n';
				if (hookFailureHandler != null) {
					final ByteArrayOutputStream errorByteArray = new ByteArrayOutputStream();
					final PrintStream hookErrRedirect = new PrintStream(
							errorByteArray);
					ProcessResult postRewriteHookResult = FS.DETECTED
							.runIfPresent(repo
									new String[] { "amend" }
									hookErrRedirect
					if (postRewriteHookResult.getStatus() == ProcessResult.Status.OK
							&& postRewriteHookResult.getExitCode() != 0) {
						hookFailureHandler.hookExecutionFailed(
								Hook.POST_REWRITE
								errorByteArray.toString());
					}
				} else {
					FS.DETECTED.runIfPresent(repo
							new String[] { "amend" }
							System.err
				}
			}

