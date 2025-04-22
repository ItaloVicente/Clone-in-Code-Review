	private static Map<String, ReceiveCommand> byName(
			List<ReceiveCommand> commands) {
		Map<String, ReceiveCommand> ret = new LinkedHashMap<>();
		for (ReceiveCommand cmd : commands) {
			ret.put(cmd.getRefName(), cmd);
		}
		return ret;
	}

