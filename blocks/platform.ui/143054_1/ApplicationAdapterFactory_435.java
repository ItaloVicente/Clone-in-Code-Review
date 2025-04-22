	protected ApplicationSwitch<Adapter> modelSwitch = new ApplicationSwitch<Adapter>() {
		@Override
		public Adapter caseStringToStringMap(Map.Entry<String, String> object) {
			return createStringToStringMapAdapter();
		}

		@Override
		public Adapter caseApplication(MApplication object) {
			return createApplicationAdapter();
		}

		@Override
		public Adapter caseApplicationElement(MApplicationElement object) {
			return createApplicationElementAdapter();
		}

		@Override
		public Adapter caseContribution(MContribution object) {
			return createContributionAdapter();
		}

		@Override
		public Adapter caseAddon(MAddon object) {
			return createAddonAdapter();
		}

		@Override
		public Adapter caseStringToObjectMap(Map.Entry<String, Object> object) {
			return createStringToObjectMapAdapter();
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
		public <T extends MUIElement> Adapter caseElementContainer(MElementContainer<T> object) {
			return createElementContainerAdapter();
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
		public Adapter caseBindingTableContainer(MBindingTableContainer object) {
			return createBindingTableContainerAdapter();
		}

		@Override
		public Adapter casePartDescriptorContainer(MPartDescriptorContainer object) {
			return createPartDescriptorContainerAdapter();
		}

		@Override
		public Adapter caseBindings(MBindings object) {
			return createBindingsAdapter();
		}

		@Override
		public Adapter caseMenuContributions(MMenuContributions object) {
			return createMenuContributionsAdapter();
		}

		@Override
		public Adapter caseToolBarContributions(MToolBarContributions object) {
			return createToolBarContributionsAdapter();
		}

		@Override
		public Adapter caseTrimContributions(MTrimContributions object) {
			return createTrimContributionsAdapter();
		}

		@Override
		public Adapter caseSnippetContainer(MSnippetContainer object) {
			return createSnippetContainerAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};
