
		try {
			recvCommands();
		} catch (PackProtocolException e) {
			fatalError(e.getMessage());
			throw e;
		} catch (InputOverLimitIOException e) {
			String msg = JGitText.get().tooManyCommands;
			fatalError(msg);
			throw new PackProtocolException(msg);
		}

