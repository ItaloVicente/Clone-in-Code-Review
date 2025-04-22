		deprecatedTypeMappings.put("OpaqueMenu", new ObjectCreator() { //$NON-NLS-1$

					@Override
					public MApplicationElement create() {
						return OpaqueElementUtil.createOpaqueMenu();
					}
				});
		deprecatedTypeMappings.put("OpaqueMenuItem", new ObjectCreator() { //$NON-NLS-1$

					@Override
					public MApplicationElement create() {
						return OpaqueElementUtil.createOpaqueMenuItem();
					}
				});
		deprecatedTypeMappings.put("OpaqueMenuSeparator", new ObjectCreator() { //$NON-NLS-1$

					@Override
					public MApplicationElement create() {
						return OpaqueElementUtil.createOpaqueMenuSeparator();
					}
				});
		deprecatedTypeMappings.put("OpaqueToolItem", new ObjectCreator() { //$NON-NLS-1$

					@Override
					public MApplicationElement create() {
						return OpaqueElementUtil.createOpaqueToolItem();
					}
				});
		deprecatedTypeMappings.put("RenderedMenu", new ObjectCreator() { //$NON-NLS-1$

					@Override
					public MApplicationElement create() {
						return RenderedElementUtil.createRenderedMenu();
					}
				});
		deprecatedTypeMappings.put("RenderedMenuItem", new ObjectCreator() { //$NON-NLS-1$

					@Override
					public MApplicationElement create() {
						return RenderedElementUtil.createRenderedMenuItem();
					}
				});
		deprecatedTypeMappings.put("RenderedToolBar", new ObjectCreator() { //$NON-NLS-1$

					@Override
					public MApplicationElement create() {
						return RenderedElementUtil.createRenderedToolBar();
					}
				});
