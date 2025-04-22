	 * enabled when that shell is active. If a shell is registered as a dialog
	 * -- or is not registered, but has a parent shell -- then the "In Dialogs"
	 * context is enabled when that shell is active. If the shell is registered
	 * as none -- or is not registered, but has no parent shell -- then the
	 * neither of the contexts will be enabled (by us -- someone else can always
	 * enabled them).
