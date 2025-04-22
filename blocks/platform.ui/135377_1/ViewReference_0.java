		if (view == null)
			return false;

		XMLMemento root = XMLMemento.createWriteRoot("view"); //$NON-NLS-1$
		view.saveState(root);
		StringWriter writer = new StringWriter();
		try {
			root.save(writer);
			getModel().getPersistedState().put(MEMENTO_KEY, writer.toString());
		} catch (IOException e) {
			WorkbenchPlugin.log(e);
			return false;
