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

