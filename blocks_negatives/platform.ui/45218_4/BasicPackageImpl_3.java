	public static BasicPackageImpl init() {
		if (isInited) return (BasicPackageImpl)EPackage.Registry.INSTANCE.getEPackage(BasicPackageImpl.eNS_URI);

		BasicPackageImpl theBasicPackage = (BasicPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof BasicPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new BasicPackageImpl());

		isInited = true;

		ApplicationPackageImpl theApplicationPackage = (ApplicationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ApplicationPackageImpl.eNS_URI) instanceof ApplicationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ApplicationPackageImpl.eNS_URI) : ApplicationPackageImpl.eINSTANCE);
		CommandsPackageImpl theCommandsPackage = (CommandsPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(CommandsPackageImpl.eNS_URI) instanceof CommandsPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(CommandsPackageImpl.eNS_URI) : CommandsPackageImpl.eINSTANCE);
		UiPackageImpl theUiPackage = (UiPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(UiPackageImpl.eNS_URI) instanceof UiPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(UiPackageImpl.eNS_URI) : UiPackageImpl.eINSTANCE);
		MenuPackageImpl theMenuPackage = (MenuPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(MenuPackageImpl.eNS_URI) instanceof MenuPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(MenuPackageImpl.eNS_URI) : MenuPackageImpl.eINSTANCE);
		AdvancedPackageImpl theAdvancedPackage = (AdvancedPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(AdvancedPackageImpl.eNS_URI) instanceof AdvancedPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(AdvancedPackageImpl.eNS_URI) : AdvancedPackageImpl.eINSTANCE);
		org.eclipse.e4.ui.model.application.descriptor.basic.impl.BasicPackageImpl theBasicPackage_1 = (org.eclipse.e4.ui.model.application.descriptor.basic.impl.BasicPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(org.eclipse.e4.ui.model.application.descriptor.basic.impl.BasicPackageImpl.eNS_URI) instanceof org.eclipse.e4.ui.model.application.descriptor.basic.impl.BasicPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(org.eclipse.e4.ui.model.application.descriptor.basic.impl.BasicPackageImpl.eNS_URI) : org.eclipse.e4.ui.model.application.descriptor.basic.impl.BasicPackageImpl.eINSTANCE);

		theBasicPackage.createPackageContents();
		theApplicationPackage.createPackageContents();
		theCommandsPackage.createPackageContents();
		theUiPackage.createPackageContents();
		theMenuPackage.createPackageContents();
		theAdvancedPackage.createPackageContents();
		theBasicPackage_1.createPackageContents();

		theBasicPackage.initializePackageContents();
		theApplicationPackage.initializePackageContents();
		theCommandsPackage.initializePackageContents();
		theUiPackage.initializePackageContents();
		theMenuPackage.initializePackageContents();
		theAdvancedPackage.initializePackageContents();
		theBasicPackage_1.initializePackageContents();

		theBasicPackage.freeze();

  
		EPackage.Registry.INSTANCE.put(BasicPackageImpl.eNS_URI, theBasicPackage);
		return theBasicPackage;
	}

