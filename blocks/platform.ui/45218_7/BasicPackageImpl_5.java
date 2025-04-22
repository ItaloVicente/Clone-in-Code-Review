	public static final int WIZARD__CONTRIBUTOR_URI = UiPackageImpl.UI_LABEL_FEATURE_COUNT + 3;

	public static final int WIZARD__TRANSIENT_DATA = UiPackageImpl.UI_LABEL_FEATURE_COUNT + 4;

	public static final int WIZARD__WIDGET = UiPackageImpl.UI_LABEL_FEATURE_COUNT + 5;

	public static final int WIZARD__RENDERER = UiPackageImpl.UI_LABEL_FEATURE_COUNT + 6;

	public static final int WIZARD__TO_BE_RENDERED = UiPackageImpl.UI_LABEL_FEATURE_COUNT + 7;

	public static final int WIZARD__ON_TOP = UiPackageImpl.UI_LABEL_FEATURE_COUNT + 8;

	public static final int WIZARD__VISIBLE = UiPackageImpl.UI_LABEL_FEATURE_COUNT + 9;

	public static final int WIZARD__PARENT = UiPackageImpl.UI_LABEL_FEATURE_COUNT + 10;

	public static final int WIZARD__CONTAINER_DATA = UiPackageImpl.UI_LABEL_FEATURE_COUNT + 11;

	public static final int WIZARD__CUR_SHARED_REF = UiPackageImpl.UI_LABEL_FEATURE_COUNT + 12;

	public static final int WIZARD__VISIBLE_WHEN = UiPackageImpl.UI_LABEL_FEATURE_COUNT + 13;

	public static final int WIZARD__ACCESSIBILITY_PHRASE = UiPackageImpl.UI_LABEL_FEATURE_COUNT + 14;

	public static final int WIZARD__LOCALIZED_ACCESSIBILITY_PHRASE = UiPackageImpl.UI_LABEL_FEATURE_COUNT + 15;

	public static final int WIZARD__CHILDREN = UiPackageImpl.UI_LABEL_FEATURE_COUNT + 16;

	public static final int WIZARD__SELECTED_ELEMENT = UiPackageImpl.UI_LABEL_FEATURE_COUNT + 17;

	public static final int WIZARD_FEATURE_COUNT = UiPackageImpl.UI_LABEL_FEATURE_COUNT + 18;

	public static final int WIZARD___UPDATE_LOCALIZATION = UiPackageImpl.UI_LABEL___UPDATE_LOCALIZATION;

	public static final int WIZARD_OPERATION_COUNT = UiPackageImpl.UI_LABEL_OPERATION_COUNT + 0;

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
