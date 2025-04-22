			public Adapter caseRenderedMenu(MRenderedMenu object) {
				return createRenderedMenuAdapter();
			}
			@Override
			public Adapter caseRenderedToolBar(MRenderedToolBar object) {
				return createRenderedToolBarAdapter();
			}
			@Override
