		initializePreferenceChangeListener();
		WorkbenchPlugin.getDefault().getPreferenceStore().addPropertyChangeListener(preferenceListener);
	}

	private void initializePreferenceChangeListener() {
		preferenceListener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty().endsWith(PERSP)) {
					String id = event.getProperty().substring(0, event.getProperty().lastIndexOf(PERSP));
					if (findPerspectiveWithId(id) == null) {
						StringReader reader = new StringReader((String) event.getNewValue());
						try {
							XMLMemento memento = XMLMemento.createReadRoot(reader);
							MPerspective newPersp = createPerspectiveBuilder(new PerspectiveReader(memento))
									.createPerspective();
							application.getSnippets().add(newPersp);
						} catch (WorkbenchException e) {
							e.printStackTrace();
						}
					}
				}
			}
		};
	}

	public PerspectiveBuilder createPerspectiveBuilder(PerspectiveReader perspReader) {
		IEclipseContext childContext = context.createChild();
		childContext.set(PerspectiveReader.class, perspReader);
		return ContextInjectionFactory.make(PerspectiveBuilder.class, childContext);
