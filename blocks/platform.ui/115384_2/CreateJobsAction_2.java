		InputDialog dialog = new InputDialog(window.getShell(), "How much work?", "Enter the number of jobs to run", //$NON-NLS-1$ //$NON-NLS-2$
				"100", newText -> { //$NON-NLS-1$
					try {
						Integer.parseInt(newText);
					} catch (NumberFormatException e) {
						return "Not a number"; //$NON-NLS-1$
