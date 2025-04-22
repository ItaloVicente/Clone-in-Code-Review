	private ConcurrentHashMap<String

	public BuiltinCommandFactory registerComand(String commandName
			BuiltinCommandFactory fact) {
		if (fact == null)
			return commandRegistry.remove(commandName);
		else
			return commandRegistry.put(commandName
	}

	public boolean isRegistered(String commandName) {
		return commandRegistry.containsKey(commandName);
	}

	public BuiltinCommand getCommand(String commandName
			InputStream in
		BuiltinCommandFactory cf = commandRegistry.get(commandName);
		return (cf == null) ? null : cf.create(db
	}

