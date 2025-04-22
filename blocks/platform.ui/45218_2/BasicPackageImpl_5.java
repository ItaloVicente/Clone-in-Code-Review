	public static final int DIALOG_ELEMENT__WIDGET = UiPackageImpl.UI_ELEMENT__WIDGET;

	public static final int DIALOG_ELEMENT__RENDERER = UiPackageImpl.UI_ELEMENT__RENDERER;

	public static final int DIALOG_ELEMENT__TO_BE_RENDERED = UiPackageImpl.UI_ELEMENT__TO_BE_RENDERED;

	public static final int DIALOG_ELEMENT__ON_TOP = UiPackageImpl.UI_ELEMENT__ON_TOP;

	public static final int DIALOG_ELEMENT__VISIBLE = UiPackageImpl.UI_ELEMENT__VISIBLE;

	public static final int DIALOG_ELEMENT__PARENT = UiPackageImpl.UI_ELEMENT__PARENT;

	public static final int DIALOG_ELEMENT__CONTAINER_DATA = UiPackageImpl.UI_ELEMENT__CONTAINER_DATA;

	public static final int DIALOG_ELEMENT__CUR_SHARED_REF = UiPackageImpl.UI_ELEMENT__CUR_SHARED_REF;

	public static final int DIALOG_ELEMENT__VISIBLE_WHEN = UiPackageImpl.UI_ELEMENT__VISIBLE_WHEN;

	public static final int DIALOG_ELEMENT__ACCESSIBILITY_PHRASE = UiPackageImpl.UI_ELEMENT__ACCESSIBILITY_PHRASE;

	public static final int DIALOG_ELEMENT__LOCALIZED_ACCESSIBILITY_PHRASE = UiPackageImpl.UI_ELEMENT__LOCALIZED_ACCESSIBILITY_PHRASE;

	public static final int DIALOG_ELEMENT_FEATURE_COUNT = UiPackageImpl.UI_ELEMENT_FEATURE_COUNT + 0;

	public static final int DIALOG_ELEMENT___UPDATE_LOCALIZATION = UiPackageImpl.UI_ELEMENT___UPDATE_LOCALIZATION;

	public static final int DIALOG_ELEMENT_OPERATION_COUNT = UiPackageImpl.UI_ELEMENT_OPERATION_COUNT + 0;

	public static final int WIZARD_ELEMENT = 15;

	public static final int WIZARD_ELEMENT__ELEMENT_ID = UiPackageImpl.UI_ELEMENT__ELEMENT_ID;

	public static final int WIZARD_ELEMENT__PERSISTED_STATE = UiPackageImpl.UI_ELEMENT__PERSISTED_STATE;

	public static final int WIZARD_ELEMENT__TAGS = UiPackageImpl.UI_ELEMENT__TAGS;

	public static final int WIZARD_ELEMENT__CONTRIBUTOR_URI = UiPackageImpl.UI_ELEMENT__CONTRIBUTOR_URI;

	public static final int WIZARD_ELEMENT__TRANSIENT_DATA = UiPackageImpl.UI_ELEMENT__TRANSIENT_DATA;

	public static final int WIZARD_ELEMENT__WIDGET = UiPackageImpl.UI_ELEMENT__WIDGET;

	public static final int WIZARD_ELEMENT__RENDERER = UiPackageImpl.UI_ELEMENT__RENDERER;

	public static final int WIZARD_ELEMENT__TO_BE_RENDERED = UiPackageImpl.UI_ELEMENT__TO_BE_RENDERED;

	public static final int WIZARD_ELEMENT__ON_TOP = UiPackageImpl.UI_ELEMENT__ON_TOP;

	public static final int WIZARD_ELEMENT__VISIBLE = UiPackageImpl.UI_ELEMENT__VISIBLE;

	public static final int WIZARD_ELEMENT__PARENT = UiPackageImpl.UI_ELEMENT__PARENT;

	public static final int WIZARD_ELEMENT__CONTAINER_DATA = UiPackageImpl.UI_ELEMENT__CONTAINER_DATA;

	public static final int WIZARD_ELEMENT__CUR_SHARED_REF = UiPackageImpl.UI_ELEMENT__CUR_SHARED_REF;

	public static final int WIZARD_ELEMENT__VISIBLE_WHEN = UiPackageImpl.UI_ELEMENT__VISIBLE_WHEN;

	public static final int WIZARD_ELEMENT__ACCESSIBILITY_PHRASE = UiPackageImpl.UI_ELEMENT__ACCESSIBILITY_PHRASE;

	public static final int WIZARD_ELEMENT__LOCALIZED_ACCESSIBILITY_PHRASE = UiPackageImpl.UI_ELEMENT__LOCALIZED_ACCESSIBILITY_PHRASE;

	public static final int WIZARD_ELEMENT_FEATURE_COUNT = UiPackageImpl.UI_ELEMENT_FEATURE_COUNT + 0;

	public static final int WIZARD_ELEMENT___UPDATE_LOCALIZATION = UiPackageImpl.UI_ELEMENT___UPDATE_LOCALIZATION;

	public static final int WIZARD_ELEMENT_OPERATION_COUNT = UiPackageImpl.UI_ELEMENT_OPERATION_COUNT + 0;

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

	private EClass dialogEClass = null;

	private EClass wizardEClass = null;

	private EClass dialogElementEClass = null;

	private EClass wizardElementEClass = null;

	private EClass frameEClass = null;

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

	public EAttribute getPart_Closeable() {
		return (EAttribute)partEClass.getEStructuralFeatures().get(2);
	}

	public EAttribute getPart_Description() {
		return (EAttribute)partEClass.getEStructuralFeatures().get(3);
	}


	public EAttribute getPart_LocalizedDescription() {
		return (EAttribute)partEClass.getEStructuralFeatures().get(4);
	}


	public EReference getPart_Dialogs() {
		return (EReference)partEClass.getEStructuralFeatures().get(5);
	}


	public EClass getCompositePart() {
		return compositePartEClass;
	}


