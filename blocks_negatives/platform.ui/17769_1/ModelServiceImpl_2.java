		if (MAddon.class.equals(elementType)) {
			return (T) MApplicationFactory.INSTANCE.createAddon();
		}
		if (MApplication.class.equals(elementType)) {
			return (T) MApplicationFactory.INSTANCE.createApplication();
		}
		if (MArea.class.equals(elementType)) {
			return (T) MAdvancedFactory.INSTANCE.createArea();
		}
		if (MBindingContext.class.equals(elementType)) {
			return (T) MCommandsFactory.INSTANCE.createBindingContext();
		}
		if (MBindingTable.class.equals(elementType)) {
			return (T) MCommandsFactory.INSTANCE.createBindingTable();
		}
		if (MCategory.class.equals(elementType)) {
			return (T) MCommandsFactory.INSTANCE.createCategory();
		}
		if (MCommand.class.equals(elementType)) {
			return (T) MCommandsFactory.INSTANCE.createCommand();
		}
		if (MCommandParameter.class.equals(elementType)) {
			return (T) MCommandsFactory.INSTANCE.createCommandParameter();
		}
		if (MCompositePart.class.equals(elementType)) {
			return (T) MBasicFactory.INSTANCE.createCompositePart();
		}
		if (MCoreExpression.class.equals(elementType)) {
			return (T) MUiFactory.INSTANCE.createCoreExpression();
		}
		if (MDirectMenuItem.class.equals(elementType)) {
			return (T) MMenuFactory.INSTANCE.createDirectMenuItem();
		}
		if (MDirectToolItem.class.equals(elementType)) {
			return (T) MMenuFactory.INSTANCE.createDirectToolItem();
		}
		if (MDynamicMenuContribution.class.equals(elementType)) {
			return (T) MMenuFactory.INSTANCE.createDynamicMenuContribution();
		}
		if (MHandledMenuItem.class.equals(elementType)) {
			return (T) MMenuFactory.INSTANCE.createHandledMenuItem();
		}
		if (MHandledToolItem.class.equals(elementType)) {
			return (T) MMenuFactory.INSTANCE.createHandledToolItem();
		}
		if (MHandler.class.equals(elementType)) {
			return (T) MCommandsFactory.INSTANCE.createHandler();
		}
		if (MInputPart.class.equals(elementType)) {
			return (T) MBasicFactory.INSTANCE.createInputPart();
		}
		if (MKeyBinding.class.equals(elementType)) {
			return (T) MCommandsFactory.INSTANCE.createKeyBinding();
		}
		if (MMenu.class.equals(elementType)) {
			return (T) MMenuFactory.INSTANCE.createMenu();
		}
		if (MMenuContribution.class.equals(elementType)) {
			return (T) MMenuFactory.INSTANCE.createMenuContribution();
		}
		if (MMenuSeparator.class.equals(elementType)) {
			return (T) MMenuFactory.INSTANCE.createMenuSeparator();
		}
		if (MParameter.class.equals(elementType)) {
			return (T) MCommandsFactory.INSTANCE.createParameter();
		}
		if (MPart.class.equals(elementType)) {
			return (T) MBasicFactory.INSTANCE.createPart();
		}
		if (MPartDescriptor.class.equals(elementType)) {
			return (T) org.eclipse.e4.ui.model.application.descriptor.basic.MBasicFactory.INSTANCE
					.createPartDescriptor();
		}
		if (MPartSashContainer.class.equals(elementType)) {
			return (T) MBasicFactory.INSTANCE.createPartSashContainer();
		}
		if (MPartStack.class.equals(elementType)) {
			return (T) MBasicFactory.INSTANCE.createPartStack();
		}
		if (MPerspective.class.equals(elementType)) {
			return (T) MAdvancedFactory.INSTANCE.createPerspective();
		}
		if (MPerspectiveStack.class.equals(elementType)) {
			return (T) MAdvancedFactory.INSTANCE.createPerspectiveStack();
		}
		if (MPlaceholder.class.equals(elementType)) {
			return (T) MAdvancedFactory.INSTANCE.createPlaceholder();
		}
		if (MPopupMenu.class.equals(elementType)) {
			return (T) MMenuFactory.INSTANCE.createPopupMenu();
		}
		if (MToolBar.class.equals(elementType)) {
			return (T) MMenuFactory.INSTANCE.createToolBar();
		}
		if (MToolBarContribution.class.equals(elementType)) {
			return (T) MMenuFactory.INSTANCE.createToolBarContribution();
		}
		if (MToolBarSeparator.class.equals(elementType)) {
			return (T) MMenuFactory.INSTANCE.createToolBarSeparator();
		}
		if (MToolControl.class.equals(elementType)) {
			return (T) MMenuFactory.INSTANCE.createToolControl();
		}
		if (MTrimBar.class.equals(elementType)) {
			return (T) MBasicFactory.INSTANCE.createTrimBar();
		}
		if (MTrimContribution.class.equals(elementType)) {
			return (T) MMenuFactory.INSTANCE.createTrimContribution();
		}
		if (MTrimmedWindow.class.equals(elementType)) {
			return (T) MBasicFactory.INSTANCE.createTrimmedWindow();
		}
		if (MWindow.class.equals(elementType)) {
			return (T) MBasicFactory.INSTANCE.createWindow();
