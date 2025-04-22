	private static boolean containsSymrefs(List<ReceiveCommand> commands) {
		for (ReceiveCommand cmd : commands) {
			if (cmd.getOldSymref() != null || cmd.getNewSymref() != null) {
				return true;
			}
		}
		return false;
	}

