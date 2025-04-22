	 * This is a map of shell to a list of activations. When a shell is
	 * registered, it is added to this map with the list of activation that
	 * should be submitted when the shell is active. When the shell is
	 * deactivated, this same list should be withdrawn. A shell is removed from
	 * this map using the {@link #unregisterShell(Shell)}method. This value may
	 * be empty, but is never <code>null</code>. The <code>null</code> key
	 * is reserved for active shells that have not been registered but have a
	 * parent (i.e., default dialog service).
