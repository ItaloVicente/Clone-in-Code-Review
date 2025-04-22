	protected boolean needPack() {
		for (ReceiveCommand cmd : commands) {
			if (cmd.getType() != ReceiveCommand.Type.DELETE)
				return true;
		}
		return false;
