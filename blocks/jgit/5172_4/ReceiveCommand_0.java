	public static List<ReceiveCommand> filter(List<ReceiveCommand> commands
			final Result want) {
		List<ReceiveCommand> r = new ArrayList<ReceiveCommand>(commands.size());
		for (final ReceiveCommand cmd : commands) {
			if (cmd.getResult() == want)
				r.add(cmd);
		}
		return r;
	}

