	public static CommandManagerLegacyWrapper getCommandManagerWrapper(
			final BindingManager bindingManager,
			final CommandManager commandManager,
			final ContextManager contextManager) {
		return new CommandManagerLegacyWrapper(bindingManager, commandManager,
				contextManager);
