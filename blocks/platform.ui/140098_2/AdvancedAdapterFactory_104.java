	protected AdvancedSwitch<Adapter> modelSwitch = new AdvancedSwitch<Adapter>() {
		@Override
		public Adapter casePlaceholder(MPlaceholder object) {
			return createPlaceholderAdapter();
		}

		@Override
		public Adapter casePerspective(MPerspective object) {
			return createPerspectiveAdapter();
		}

		@Override
		public Adapter casePerspectiveStack(MPerspectiveStack object) {
			return createPerspectiveStackAdapter();
		}

		@Override
		public Adapter caseArea(MArea object) {
			return createAreaAdapter();
		}

		@Override
		public Adapter caseApplicationElement(MApplicationElement object) {
			return createApplicationElementAdapter();
		}

		@Override
		public Adapter caseLocalizable(MLocalizable object) {
			return createLocalizableAdapter();
		}

		@Override
		public Adapter caseUIElement(MUIElement object) {
			return createUIElementAdapter();
		}

		@Override
		public Adapter casePartSashContainerElement(MPartSashContainerElement object) {
			return createPartSashContainerElementAdapter();
		}

		@Override
		public Adapter caseStackElement(MStackElement object) {
			return createStackElementAdapter();
		}

		@Override
		public <T extends MUIElement> Adapter caseElementContainer(MElementContainer<T> object) {
			return createElementContainerAdapter();
		}

		@Override
		public Adapter caseUILabel(MUILabel object) {
			return createUILabelAdapter();
		}

		@Override
		public Adapter caseContext(MContext object) {
			return createContextAdapter();
		}

		@Override
		public Adapter caseHandlerContainer(MHandlerContainer object) {
			return createHandlerContainerAdapter();
		}

		@Override
		public Adapter caseBindings(MBindings object) {
			return createBindingsAdapter();
		}

		@Override
		public <T extends MUIElement> Adapter caseGenericStack(MGenericStack<T> object) {
			return createGenericStackAdapter();
		}

		@Override
		public Adapter caseWindowElement(MWindowElement object) {
			return createWindowElementAdapter();
		}

		@Override
		public <T extends MUIElement> Adapter caseGenericTile(MGenericTile<T> object) {
			return createGenericTileAdapter();
		}

		@Override
		public Adapter casePartSashContainer(MPartSashContainer object) {
			return createPartSashContainerAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};
