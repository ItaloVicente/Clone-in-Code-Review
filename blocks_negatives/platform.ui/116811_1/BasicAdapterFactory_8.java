		new BasicSwitch<Adapter>() {
			@Override
			public Adapter casePart(MPart object) {
				return createPartAdapter();
			}
			@Override
			public Adapter caseCompositePart(MCompositePart object) {
				return createCompositePartAdapter();
			}
			@Override
			public Adapter caseInputPart(MInputPart object) {
				return createInputPartAdapter();
			}
			@Override
			public Adapter casePartStack(MPartStack object) {
				return createPartStackAdapter();
			}
			@Override
			public Adapter casePartSashContainer(MPartSashContainer object) {
				return createPartSashContainerAdapter();
			}
			@Override
			public Adapter caseWindow(MWindow object) {
				return createWindowAdapter();
			}
			@Override
			public Adapter caseTrimmedWindow(MTrimmedWindow object) {
				return createTrimmedWindowAdapter();
			}
			@Override
			public Adapter caseTrimElement(MTrimElement object) {
				return createTrimElementAdapter();
			}
			@Override
			public Adapter casePartSashContainerElement(MPartSashContainerElement object) {
				return createPartSashContainerElementAdapter();
			}
			@Override
			public Adapter caseWindowElement(MWindowElement object) {
				return createWindowElementAdapter();
			}
			@Override
			public Adapter caseTrimBar(MTrimBar object) {
				return createTrimBarAdapter();
			}
			@Override
			public Adapter caseStackElement(MStackElement object) {
				return createStackElementAdapter();
			}
			@Override
			public Adapter caseDialog(MDialog object) {
				return createDialogAdapter();
			}
			@Override
			public Adapter caseWizardDialog(MWizardDialog object) {
				return createWizardDialogAdapter();
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
			public Adapter caseContribution(MContribution object) {
				return createContributionAdapter();
			}
			@Override
			public Adapter caseContext(MContext object) {
				return createContextAdapter();
			}
			@Override
			public Adapter caseUILabel(MUILabel object) {
				return createUILabelAdapter();
			}
			@Override
			public Adapter caseHandlerContainer(MHandlerContainer object) {
				return createHandlerContainerAdapter();
			}
			@Override
			public Adapter caseDirtyable(MDirtyable object) {
				return createDirtyableAdapter();
			}
			@Override
			public Adapter caseBindings(MBindings object) {
				return createBindingsAdapter();
			}
			@Override
			public <T extends MUIElement> Adapter caseElementContainer(MElementContainer<T> object) {
				return createElementContainerAdapter();
			}
			@Override
			public <T extends MUIElement> Adapter caseGenericTile(MGenericTile<T> object) {
				return createGenericTileAdapter();
			}
			@Override
			public Adapter caseInput(MInput object) {
				return createInputAdapter();
			}
			@Override
			public <T extends MUIElement> Adapter caseGenericStack(MGenericStack<T> object) {
				return createGenericStackAdapter();
			}
			@Override
			public Adapter caseSnippetContainer(MSnippetContainer object) {
				return createSnippetContainerAdapter();
			}
			@Override
			public <T extends MUIElement> Adapter caseGenericTrimContainer(MGenericTrimContainer<T> object) {
				return createGenericTrimContainerAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};
