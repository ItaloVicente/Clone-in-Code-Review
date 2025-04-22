			if (!noVerify
					&& FS.DETECTED.tryFindHook(repo
				exportPreparedMessage(message);
				final ByteArrayOutputStream errorByteArray = new ByteArrayOutputStream();
				final PrintStream hookErrRedirect = new PrintStream(
						errorByteArray);
				String messageFilePath = getMessageFile(false)
						.getAbsolutePath();
				messageFilePath = messageFilePath.replace(File.separatorChar
						'/');
				ProcessResult commitMsgHookResult = FS.DETECTED.runIfPresent(
						repo
						Hook.COMMIT_MSG
						System.out
				final String errorDetails = errorByteArray.toString();
				if (commitMsgHookResult.getStatus() == ProcessResult.Status.OK
						&& commitMsgHookResult.getExitCode() != 0) {
					commitRejectedByHook(Hook.COMMIT_MSG
				}
				message = readPreparedMessage();
			}

