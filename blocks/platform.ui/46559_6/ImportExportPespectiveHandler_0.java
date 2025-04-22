		addPerspectiveToWorkbench(perspective);
	}

	private void importPerspective3x(PreferenceChangeEvent event) {
		importedPersps.add(event.getKey());
		StringReader reader = new StringReader((String) event.getNewValue());
		MPerspective perspective = null;
		IEclipseContext childContext = context.createChild();
		try {
			childContext.set(PerspectiveReader.class, new PerspectiveReader(XMLMemento.createReadRoot(reader)));
			perspective = ContextInjectionFactory.make(PerspectiveBuilder.class, childContext).createPerspective();
		} catch (WorkbenchException e) {
			logError(event, e);
		} finally {
			reader.close();
			childContext.dispose();
		}
		addPerspectiveToWorkbench(perspective);
	}

	private void addPerspectiveToWorkbench(MPerspective perspective) {
