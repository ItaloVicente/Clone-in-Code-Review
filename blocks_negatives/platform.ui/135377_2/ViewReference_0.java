		if (view != null) {
			view.saveState(root);
			StringWriter writer = new StringWriter();
			try {
				root.save(writer);
				getModel().getPersistedState().put(MEMENTO_KEY, writer.toString());
			} catch (IOException e) {
				WorkbenchPlugin.log(e);
			}
