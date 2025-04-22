		} catch (PackProtocolException e) {
			discardCommands();
			fatalError(e.getMessage());
			throw e;
		} catch (InputOverLimitIOException e) {
			String msg = JGitText.get().tooManyCommands;
