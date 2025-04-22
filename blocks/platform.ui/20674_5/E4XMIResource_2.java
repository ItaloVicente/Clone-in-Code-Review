
	private interface ObjectCreator {
		MApplicationElement create();
	}

	static final Map<String, ObjectCreator> deprecatedTypeMappings = new HashMap<String, ObjectCreator>();
	static {
		deprecatedTypeMappings.put("OpaqueMenu", new ObjectCreator() { //$NON-NLS-1$

					public MApplicationElement create() {
						final MMenu object = MMenuFactory.INSTANCE.createMenu();
						object.getTags().add("Opaque"); //$NON-NLS-1$
						return object;
					}
				});
		deprecatedTypeMappings.put("OpaqueMenuItem", new ObjectCreator() { //$NON-NLS-1$

					public MApplicationElement create() {
						final MDirectMenuItem object = MMenuFactory.INSTANCE.createDirectMenuItem();
						object.getTags().add("Opaque"); //$NON-NLS-1$
						return object;
					}
				});
		deprecatedTypeMappings.put("OpaqueMenuSeparator", new ObjectCreator() { //$NON-NLS-1$

					public MApplicationElement create() {
						final MMenuSeparator object = MMenuFactory.INSTANCE.createMenuSeparator();
						object.getTags().add("Opaque"); //$NON-NLS-1$
						return object;
					}
				});
		deprecatedTypeMappings.put("OpaqueToolItem", new ObjectCreator() { //$NON-NLS-1$

					public MApplicationElement create() {
						final MDirectToolItem object = MMenuFactory.INSTANCE.createDirectToolItem();
						object.getTags().add("Opaque"); //$NON-NLS-1$
						return object;
					}
				});
		deprecatedTypeMappings.put("RenderedMenu", new ObjectCreator() { //$NON-NLS-1$

					public MApplicationElement create() {
						final MMenu object = MMenuFactory.INSTANCE.createMenu();
						object.getTags().add("Rendered"); //$NON-NLS-1$
						return object;
					}
				});
		deprecatedTypeMappings.put("RenderedMenuItem", new ObjectCreator() { //$NON-NLS-1$

					public MApplicationElement create() {
						final MDirectMenuItem object = MMenuFactory.INSTANCE.createDirectMenuItem();
						object.getTags().add("Rendered"); //$NON-NLS-1$
						return object;
					}
				});
		deprecatedTypeMappings.put("RenderedToolBar", new ObjectCreator() { //$NON-NLS-1$

					public MApplicationElement create() {
						final MToolBar object = MMenuFactory.INSTANCE.createToolBar();
						object.getTags().add("Rendered"); //$NON-NLS-1$
						return object;
					}
				});
	}

	@Override
	protected XMLHelper createXMLHelper() {
		return new XMIHelperImpl(this) {

			@Override
			public EObject createObject(EFactory eFactory, EClassifier type) {
				if (MMenuFactory.INSTANCE == eFactory) {
					final ObjectCreator objectCreator = deprecatedTypeMappings.get(type.getName());
					if (objectCreator != null) {
						return (EObject) objectCreator.create();
					}
				}
				return super.createObject(eFactory, type);
			}

		};
	}
