	protected MenuSwitch<Adapter> modelSwitch =
		new MenuSwitch<Adapter>() {
			@Override
			public Adapter caseItem(MItem object) {
				return createItemAdapter();
			}
			@Override
			public Adapter caseHandledItem(MHandledItem object) {
				return createHandledItemAdapter();
			}
			@Override
			public Adapter caseMenuElement(MMenuElement object) {
				return createMenuElementAdapter();
			}
			@Override
			public Adapter caseMenuItem(MMenuItem object) {
				return createMenuItemAdapter();
			}
			@Override
			public Adapter caseMenuSeparator(MMenuSeparator object) {
				return createMenuSeparatorAdapter();
			}
			@Override
			public Adapter caseMenu(MMenu object) {
				return createMenuAdapter();
			}
			@Override
			public Adapter caseMenuContribution(MMenuContribution object) {
				return createMenuContributionAdapter();
			}
			@Override
			public Adapter casePopupMenu(MPopupMenu object) {
				return createPopupMenuAdapter();
			}
			@Override
			public Adapter caseDirectMenuItem(MDirectMenuItem object) {
				return createDirectMenuItemAdapter();
			}
			@Override
			public Adapter caseHandledMenuItem(MHandledMenuItem object) {
				return createHandledMenuItemAdapter();
			}
			@Override
			public Adapter caseToolItem(MToolItem object) {
				return createToolItemAdapter();
			}
			@Override
			public Adapter caseToolBar(MToolBar object) {
				return createToolBarAdapter();
			}
			@Override
			public Adapter caseToolBarElement(MToolBarElement object) {
				return createToolBarElementAdapter();
			}
			@Override
			public Adapter caseToolControl(MToolControl object) {
				return createToolControlAdapter();
			}
			@Override
			public Adapter caseHandledToolItem(MHandledToolItem object) {
				return createHandledToolItemAdapter();
			}
			@Override
			public Adapter caseDirectToolItem(MDirectToolItem object) {
				return createDirectToolItemAdapter();
			}
			@Override
			public Adapter caseToolBarSeparator(MToolBarSeparator object) {
				return createToolBarSeparatorAdapter();
			}
			@Override
			public Adapter caseMenuContributions(MMenuContributions object) {
				return createMenuContributionsAdapter();
			}
			@Override
			public Adapter caseToolBarContribution(MToolBarContribution object) {
				return createToolBarContributionAdapter();
			}
			@Override
			public Adapter caseToolBarContributions(MToolBarContributions object) {
				return createToolBarContributionsAdapter();
			}
			@Override
			public Adapter caseTrimContribution(MTrimContribution object) {
				return createTrimContributionAdapter();
			}
			@Override
			public Adapter caseTrimContributions(MTrimContributions object) {
				return createTrimContributionsAdapter();
			}
			@Override
			public Adapter caseDynamicMenuContribution(MDynamicMenuContribution object) {
				return createDynamicMenuContributionAdapter();
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
			public Adapter caseUILabel(MUILabel object) {
				return createUILabelAdapter();
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
			public Adapter caseContribution(MContribution object) {
				return createContributionAdapter();
			}
			@Override
			public Adapter caseTrimElement(MTrimElement object) {
				return createTrimElementAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};
