	private static ConcurrentHashMap<String

	public static FilterCommandFactory registerFilterCommand(String filterCommandName
			FilterCommandFactory factory) {
		if (factory == null)
			return filterCommandRegistry.remove(filterCommandName);
		else
			return filterCommandRegistry.put(filterCommandName
	}

	public static boolean isRegistered(String filterCommandName) {
		return filterCommandRegistry.containsKey(filterCommandName);
	}

	public static FilterCommand getFilterCommand(String filterCommandName
			Repository db
			throws IOException {
		FilterCommandFactory cf = filterCommandRegistry.get(filterCommandName);
		return (cf == null) ? null : cf.create(db
	}

