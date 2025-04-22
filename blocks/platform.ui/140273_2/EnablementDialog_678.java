		super.configureShell(newShell);
		newShell.setText(RESOURCE_BUNDLE.getString("title")); //$NON-NLS-1$
	}

	public boolean getDontAsk() {
		return dontAsk;
	}

