		try {
			service();
		} catch (PackProtocolException e) {
			fatalError(e.getMessage());
			throw e;
		} catch (InputOverLimitIOException e) {
			String msg = JGitText.get().tooManyCommands;
			fatalError(msg);
			throw new PackProtocolException(msg);
		} finally {
			try {
				close();
			} finally {
				release();
			}
		}
	}

	public void receiveWithExceptionPropagation(InputStream input
			OutputStream output
		init(input
