			if (!noVerify
					&& FS.DETECTED.findHook(repo
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
						new String[] { messageFilePath
						hookErrRedirect
				final String errorDetails = errorByteArray.toString();
				if (commitMsgHookResult.getStatus() == ProcessResult.Status.OK
						&& commitMsgHookResult.getExitCode() != 0) {
					String errorMessage = MessageFormat.format(
							JGitText.get().commitRejectedByHook
							Hook.COMMIT_MSG.getName()
					throw new RejectCommitException(errorMessage);
				}
				message = readPreparedMessage();
			}

