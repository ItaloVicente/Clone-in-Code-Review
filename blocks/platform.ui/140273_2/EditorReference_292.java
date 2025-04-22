			String memento = getModel().getPersistedState().get(MEMENTO_KEY);
			if (memento == null) {
				descriptorId = EditorRegistry.EMPTY_EDITOR_ID;
			} else {
				XMLMemento createReadRoot;
				try {
					createReadRoot = XMLMemento.createReadRoot(new StringReader(memento));
				} catch (WorkbenchException e) {
					WorkbenchPlugin.log(e);
