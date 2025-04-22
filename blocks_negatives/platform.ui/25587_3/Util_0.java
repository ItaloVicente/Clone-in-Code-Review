	/**
	 * Returns context help ID which is directly assigned to the command.
	 * Context help IDs assigned to related handlers are ignored.
	 *
	 * @param command
	 *            The command from which the context help ID is retrieved.
	 * @return The help context ID assigned to the command; may be
	 *         <code>null</code>.
	 */
	public static final String getHelpContextId(Command command) {
		Method method = null;
		try {
			method = Command.class.getDeclaredMethod("getHelpContextId", null); //$NON-NLS-1$
		} catch (Exception e) {
		}

		String contextId = null;
		if (method != null) {
			boolean accessible = method.isAccessible();
			method.setAccessible(true);
			try {
				contextId = (String) method.invoke(command, null);
			} catch (Exception e) {
			}
			method.setAccessible(accessible);
		}
		return contextId;
	}

