			int commandTimeout = timeout;
			if (timeout > 0) {
				elapsed = System.nanoTime() - elapsed;
				commandTimeout -= (int) TimeUnit.NANOSECONDS.toSeconds(elapsed);
				if (commandTimeout <= 0) {
					throw new CommandFailedException(0
							MessageFormat.format(
									JGitText.get().sshCommandTimeout
									Integer.valueOf(timeout)));
				}
			}
			process = session.exec(command
