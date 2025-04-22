				try {
					if (process.exitValue() != 0) {
						failure = new CommandFailedException(
								process.exitValue()
								MessageFormat.format(
										JGitText.get().sshCommandFailed
										command
					}
					out = stdout.toString();
				} catch (IllegalThreadStateException e) {
					failure = new CommandFailedException(0
