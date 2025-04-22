			try {
				apply(isChecked());
			} catch (RuntimeException e) {
				Activator.handleError("Error during preference change handler", //$NON-NLS-1$
						e, false);
			}
