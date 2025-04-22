		InputDialog dialog = new InputDialog(window.getShell(), "How long?", "Enter the number of milliseconds per job", "1000", new IInputValidator() { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					@Override
					public String isValid(String newText) {
						try {
							Long.parseLong(newText);
						} catch (NumberFormatException e) {
						}
						return null;
