				if (process.exitValue() != 0) {
					failure = new CommandFailedException(process.exitValue()
							MessageFormat.format(
							JGitText.get().sshCommandFailed
							stderr.toString()));
				}
