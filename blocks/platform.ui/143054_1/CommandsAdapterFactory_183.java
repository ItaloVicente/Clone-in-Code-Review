	protected CommandsSwitch<Adapter> modelSwitch = new CommandsSwitch<Adapter>() {
		@Override
		public Adapter caseBindingTableContainer(MBindingTableContainer object) {
			return createBindingTableContainerAdapter();
		}

		@Override
		public Adapter caseBindings(MBindings object) {
			return createBindingsAdapter();
		}

		@Override
		public Adapter caseBindingContext(MBindingContext object) {
			return createBindingContextAdapter();
		}

		@Override
		public Adapter caseBindingTable(MBindingTable object) {
			return createBindingTableAdapter();
		}

		@Override
		public Adapter caseCommand(MCommand object) {
			return createCommandAdapter();
		}

		@Override
		public Adapter caseCommandParameter(MCommandParameter object) {
			return createCommandParameterAdapter();
		}

		@Override
		public Adapter caseHandler(MHandler object) {
			return createHandlerAdapter();
		}

		@Override
		public Adapter caseHandlerContainer(MHandlerContainer object) {
			return createHandlerContainerAdapter();
		}

		@Override
		public Adapter caseKeyBinding(MKeyBinding object) {
			return createKeyBindingAdapter();
		}

		@Override
		public Adapter caseKeySequence(MKeySequence object) {
			return createKeySequenceAdapter();
		}

		@Override
		public Adapter caseParameter(MParameter object) {
			return createParameterAdapter();
		}

		@Override
		public Adapter caseCategory(MCategory object) {
			return createCategoryAdapter();
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
		public Adapter caseContribution(MContribution object) {
			return createContributionAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};
