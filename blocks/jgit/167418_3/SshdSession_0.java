			} catch (IOException | RuntimeException e) {
				exec.close(true);
				throw e;
			}
		} else {
			try {
				exec.open().verify(TimeUnit.SECONDS.toMillis(timeout));
			} catch (IOException | RuntimeException e) {
				exec.close(true);
				throw new IOException(format(SshdText.get().sshCommandTimeout
						commandName
