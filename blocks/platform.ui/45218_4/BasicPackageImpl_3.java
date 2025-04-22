	public static final int WIZARD__ON_TOP = UiPackageImpl.GENERIC_STACK__ON_TOP;

	public static final int WIZARD__VISIBLE = UiPackageImpl.GENERIC_STACK__VISIBLE;

	public static final int WIZARD__PARENT = UiPackageImpl.GENERIC_STACK__PARENT;

	public static final int WIZARD__CONTAINER_DATA = UiPackageImpl.GENERIC_STACK__CONTAINER_DATA;

	public static final int WIZARD__CUR_SHARED_REF = UiPackageImpl.GENERIC_STACK__CUR_SHARED_REF;

	public static final int WIZARD__VISIBLE_WHEN = UiPackageImpl.GENERIC_STACK__VISIBLE_WHEN;

	public static final int WIZARD__ACCESSIBILITY_PHRASE = UiPackageImpl.GENERIC_STACK__ACCESSIBILITY_PHRASE;

	public static final int WIZARD__LOCALIZED_ACCESSIBILITY_PHRASE = UiPackageImpl.GENERIC_STACK__LOCALIZED_ACCESSIBILITY_PHRASE;

	public static final int WIZARD__CHILDREN = UiPackageImpl.GENERIC_STACK__CHILDREN;

	public static final int WIZARD__SELECTED_ELEMENT = UiPackageImpl.GENERIC_STACK__SELECTED_ELEMENT;

	public static final int WIZARD__LABEL = UiPackageImpl.GENERIC_STACK_FEATURE_COUNT + 0;

	public static final int WIZARD__ICON_URI = UiPackageImpl.GENERIC_STACK_FEATURE_COUNT + 1;

	public static final int WIZARD__TOOLTIP = UiPackageImpl.GENERIC_STACK_FEATURE_COUNT + 2;

	public static final int WIZARD__LOCALIZED_LABEL = UiPackageImpl.GENERIC_STACK_FEATURE_COUNT + 3;

	public static final int WIZARD__LOCALIZED_TOOLTIP = UiPackageImpl.GENERIC_STACK_FEATURE_COUNT + 4;

	public static final int WIZARD_FEATURE_COUNT = UiPackageImpl.GENERIC_STACK_FEATURE_COUNT + 5;

	public static final int WIZARD___UPDATE_LOCALIZATION = UiPackageImpl.GENERIC_STACK___UPDATE_LOCALIZATION;

	public static final int WIZARD_OPERATION_COUNT = UiPackageImpl.GENERIC_STACK_OPERATION_COUNT + 0;

	private EClass partEClass = null;

	private EClass compositePartEClass = null;

	private EClass inputPartEClass = null;

	private EClass partStackEClass = null;

	private EClass partSashContainerEClass = null;

	private EClass windowEClass = null;

	private EClass trimmedWindowEClass = null;

	private EClass trimElementEClass = null;

	private EClass partSashContainerElementEClass = null;

	private EClass windowElementEClass = null;

	private EClass trimBarEClass = null;

	private EClass stackElementEClass = null;

	private EClass frameEClass = null;

	private EClass frameElementEClass = null;

	private EClass dialogEClass = null;

	private EClass wizardEClass = null;

	private BasicPackageImpl() {
		super(eNS_URI, ((EFactory)MBasicFactory.INSTANCE));
	}

	private static boolean isInited = false;

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


	public EClass getPart() {
		return partEClass;
	}

	public EReference getPart_Menus() {
		return (EReference)partEClass.getEStructuralFeatures().get(0);
	}

	public EReference getPart_Toolbar() {
		return (EReference)partEClass.getEStructuralFeatures().get(1);
	}
