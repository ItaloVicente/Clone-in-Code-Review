	private static ConcurrentHashMap<String

	public static BuiltinCommandFactory registerCommand(String commandName
			BuiltinCommandFactory fact) {
		if (fact == null)
			return commandRegistry.remove(commandName);
		else
			return commandRegistry.put(commandName
	}

	public static boolean isRegistered(String commandName) {
		return commandRegistry.containsKey(commandName);
	}

	public static BuiltinCommand getCommand(String commandName
			InputStream in
		BuiltinCommandFactory cf = commandRegistry.get(commandName);
		return (cf == null) ? null : cf.create(db
	}

