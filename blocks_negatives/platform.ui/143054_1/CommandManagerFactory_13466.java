	 * @param bindingManager
	 *            The binding manager providing support for the command manager;
	 *            must not be <code>null</code>.
	 * @param commandManager
	 *            The command manager providing support for this command
	 *            manager; must not be <code>null</code>.
	 * @param contextManager
	 *            The context manager for this command manager; must not be
	 *            <code>null</code>.
	 * @return a new instance of <code>IMutableCommandManager</code>. Clients
	 *         should not make assumptions about the concrete implementation
	 *         outside the contract of the interface. Guaranteed not to be
	 *         <code>null</code>.
