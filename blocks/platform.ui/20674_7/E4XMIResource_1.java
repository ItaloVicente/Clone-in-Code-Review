
	private interface ObjectCreator {
		MApplicationElement create();
	}

	static final Map<String, ObjectCreator> deprecatedTypeMappings = new HashMap<String, ObjectCreator>();
	static {
		deprecatedTypeMappings.put("OpaqueMenu", new ObjectCreator() { //$NON-NLS-1$

					public MApplicationElement create() {
						return OpaqueElementUtil.createOpaqueMenu();
					}
				});
		deprecatedTypeMappings.put("OpaqueMenuItem", new ObjectCreator() { //$NON-NLS-1$

					public MApplicationElement create() {
						return OpaqueElementUtil.createOpaqueMenuItem();
					}
				});
		deprecatedTypeMappings.put("OpaqueMenuSeparator", new ObjectCreator() { //$NON-NLS-1$

					public MApplicationElement create() {
						return OpaqueElementUtil.createOpaqueMenuSeparator();
					}
				});
		deprecatedTypeMappings.put("OpaqueToolItem", new ObjectCreator() { //$NON-NLS-1$

					public MApplicationElement create() {
						return OpaqueElementUtil.createOpaqueToolItem();
					}
				});
		deprecatedTypeMappings.put("RenderedMenu", new ObjectCreator() { //$NON-NLS-1$

					public MApplicationElement create() {
						return RenderedElementUtil.createRenderedMenu();
					}
				});
		deprecatedTypeMappings.put("RenderedMenuItem", new ObjectCreator() { //$NON-NLS-1$

					public MApplicationElement create() {
						return RenderedElementUtil.createRenderedMenuItem();
					}
				});
		deprecatedTypeMappings.put("RenderedToolBar", new ObjectCreator() { //$NON-NLS-1$

					public MApplicationElement create() {
						return RenderedElementUtil.createRenderedToolBar();
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
