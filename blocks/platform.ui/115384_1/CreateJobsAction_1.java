		InputDialog dialog = new InputDialog(window.getShell(), "How long?", "Enter the number of milliseconds per job", //$NON-NLS-1$ //$NON-NLS-2$
				"1000", newText -> { //$NON-NLS-1$
					try {
						Long.parseLong(newText);
					} catch (NumberFormatException e) {
						return "Not a number"; //$NON-NLS-1$
