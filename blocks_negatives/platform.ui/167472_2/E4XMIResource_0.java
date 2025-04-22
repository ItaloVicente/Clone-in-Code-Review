	/**
	 * Functional interface for creating objects
	 */
	private interface ObjectCreator {
		MApplicationElement create();
	}

	static final Map<String, ObjectCreator> deprecatedTypeMappings = new HashMap<>();
	static {
		deprecatedTypeMappings.put("OpaqueMenu", OpaqueElementUtil::createOpaqueMenu //$NON-NLS-1$
				);
		deprecatedTypeMappings.put("OpaqueMenuItem", OpaqueElementUtil::createOpaqueMenuItem //$NON-NLS-1$
				);
		deprecatedTypeMappings.put("OpaqueMenuSeparator", OpaqueElementUtil::createOpaqueMenuSeparator //$NON-NLS-1$
				);
		deprecatedTypeMappings.put("OpaqueToolItem", OpaqueElementUtil::createOpaqueToolItem //$NON-NLS-1$
				);
		deprecatedTypeMappings.put("RenderedMenu", RenderedElementUtil::createRenderedMenu //$NON-NLS-1$
				);
		deprecatedTypeMappings.put("RenderedMenuItem", RenderedElementUtil::createRenderedMenuItem //$NON-NLS-1$
				);
		deprecatedTypeMappings.put("RenderedToolBar", RenderedElementUtil::createRenderedToolBar //$NON-NLS-1$
				);
	}

	@Override
	protected XMLHelper createXMLHelper() {
		return new XMIHelperImpl(this) {

			@Override
			public EObject createObject(EFactory eFactory, EClassifier type) {
				if (MMenuFactory.INSTANCE == eFactory && type != null && type.getName() != null) {
					final ObjectCreator objectCreator = deprecatedTypeMappings.get(type.getName());
					if (objectCreator != null) {
						return (EObject) objectCreator.create();
					}
				}
				return super.createObject(eFactory, type);
			}

			@Override
			public EClassifier getType(EFactory eFactory, String typeName) {
				if (deprecatedTypeMappings.containsKey(typeName)) {
					final EClass tempEClass = EcoreFactory.eINSTANCE.createEClass();
					tempEClass.setName(typeName);
					return tempEClass;
				}
				return super.getType(eFactory, typeName);
			}

		};
	}

