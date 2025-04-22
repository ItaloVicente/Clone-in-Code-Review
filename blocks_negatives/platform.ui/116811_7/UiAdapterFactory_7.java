	protected UiSwitch<Adapter> modelSwitch =
		new UiSwitch<Adapter>() {
			@Override
			public Adapter caseContext(MContext object) {
				return createContextAdapter();
			}
			@Override
			public Adapter caseDirtyable(MDirtyable object) {
				return createDirtyableAdapter();
			}
			@Override
			public Adapter caseInput(MInput object) {
				return createInputAdapter();
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
			public Adapter caseUILabel(MUILabel object) {
				return createUILabelAdapter();
			}
			@Override
			public <T extends MUIElement> Adapter caseGenericStack(MGenericStack<T> object) {
				return createGenericStackAdapter();
			}
			@Override
			public <T extends MUIElement> Adapter caseGenericTile(MGenericTile<T> object) {
				return createGenericTileAdapter();
			}
			@Override
			public <T extends MUIElement> Adapter caseGenericTrimContainer(MGenericTrimContainer<T> object) {
				return createGenericTrimContainerAdapter();
			}
			@Override
			public Adapter caseExpression(MExpression object) {
				return createExpressionAdapter();
			}
			@Override
			public Adapter caseCoreExpression(MCoreExpression object) {
				return createCoreExpressionAdapter();
			}
			@Override
			public Adapter caseImperativeExpression(MImperativeExpression object) {
				return createImperativeExpressionAdapter();
			}
			@Override
			public Adapter caseSnippetContainer(MSnippetContainer object) {
				return createSnippetContainerAdapter();
			}
			@Override
			public Adapter caseLocalizable(MLocalizable object) {
				return createLocalizableAdapter();
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
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};
