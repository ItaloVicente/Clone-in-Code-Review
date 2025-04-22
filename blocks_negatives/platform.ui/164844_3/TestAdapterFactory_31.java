		@Override
		public Adapter caseTestHarness(MTestHarness object) {
			return createTestHarnessAdapter();
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
		public Adapter caseCommand(MCommand object) {
			return createCommandAdapter();
		}

		@Override
		public Adapter caseContext(MContext object) {
			return createContextAdapter();
		}

		@Override
		public Adapter caseContribution(MContribution object) {
			return createContributionAdapter();
		}

		@Override
		public Adapter caseUIElement(MUIElement object) {
			return createUIElementAdapter();
		}

		@Override
		public <T extends MUIElement> Adapter caseElementContainer(
				MElementContainer<T> object) {
			return createElementContainerAdapter();
		}

		@Override
		public Adapter caseParameter(MParameter object) {
			return createParameterAdapter();
		}

		@Override
		public Adapter caseInput(MInput object) {
			return createInputAdapter();
		}

		@Override
		public Adapter caseUILabel(MUILabel object) {
			return createUILabelAdapter();
		}

		@Override
		public Adapter caseDirtyable(MDirtyable object) {
			return createDirtyableAdapter();
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
