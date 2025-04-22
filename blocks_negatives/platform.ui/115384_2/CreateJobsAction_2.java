		InputDialog dialog = new InputDialog(window.getShell(), "How much work?", "Enter the number of jobs to run", "100", new IInputValidator() { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					@Override
					public String isValid(String newText) {
						try {
							Integer.parseInt(newText);
						} catch (NumberFormatException e) {
						}
						return null;
