			ReceiveCommand cmd;
			try {
				cmd = parseCommand(line);
			} catch (PackProtocolException e) {
				sendError(e.getMessage());
				throw e;
			}
			if (cmd.getRefName().equals(Constants.HEAD)) {
				cmd.setResult(Result.REJECTED_CURRENT_BRANCH);
			} else {
				cmd.setRef(refs.get(cmd.getRefName()));
			}
			commands.add(cmd);
			if (certParser.enabled()) {
				certParser.addCommand(cmd, rawLine);
