			public Adapter caseRenderedMenuItem(MRenderedMenuItem object) {
				return createRenderedMenuItemAdapter();
			}
			@Override
			public Adapter caseOpaqueToolItem(MOpaqueToolItem object) {
				return createOpaqueToolItemAdapter();
			}
			@Override
			public Adapter caseOpaqueMenuItem(MOpaqueMenuItem object) {
				return createOpaqueMenuItemAdapter();
			}
			@Override
			public Adapter caseOpaqueMenuSeparator(MOpaqueMenuSeparator object) {
				return createOpaqueMenuSeparatorAdapter();
			}
			@Override
			public Adapter caseOpaqueMenu(MOpaqueMenu object) {
				return createOpaqueMenuAdapter();
			}
			@Override
