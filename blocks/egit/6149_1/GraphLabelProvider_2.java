		final SWTCommit c = (SWTCommit) element;
		try {
			c.parseBody();
		} catch (IOException e) {
			Activator.error("Error parsing body", e); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
