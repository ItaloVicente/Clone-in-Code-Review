			logError(event, e);
		}
		if (perspective == null) {
			return;
		}

		addPerspectiveToRegistry(event, perspective);
	}

	private void importPerspective3x(PreferenceChangeEvent event) {
		StringReader reader = new StringReader((String) event.getNewValue());
		MPerspective perspective = null;
		try {
			perspective = createPerspectiveBuilder(new PerspectiveReader(XMLMemento.createReadRoot(reader)))
					.createPerspective();
		} catch (WorkbenchException e) {
			logError(event, e);
		} finally {
			reader.close();
