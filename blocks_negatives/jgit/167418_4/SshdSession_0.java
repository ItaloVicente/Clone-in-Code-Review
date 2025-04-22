		} catch (IOException | RuntimeException e) {
			exec.close(true);
			throw e;
		}
		if (timeout > 0 && timeoutMillis <= 0) {
			exec.close(true);
			throw new InterruptedIOException(
					format(SshdText.get().sshCommandTimeout, commandName,
							Integer.valueOf(timeout)));
