			ReceiveCommand cmd;
			try {
				cmd = parseCommand(line);
			} catch (PackProtocolException e) {
				sendError(e.getMessage());
				throw e;
