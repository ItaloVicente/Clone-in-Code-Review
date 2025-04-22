	private static ConcurrentHashMap<String

	public static FilterCommandFactory registerCommand(String commandName
			FilterCommandFactory fact) {
		if (fact == null)
			return commandRegistry.remove(commandName);
		else
			return commandRegistry.put(commandName
	}

	public static boolean isRegistered(String commandName) {
		return commandRegistry.containsKey(commandName);
	}

	public static FilterCommand getCommand(String commandName
			InputStream in
		FilterCommandFactory cf = commandRegistry.get(commandName);
		return (cf == null) ? null : cf.create(db
	}

