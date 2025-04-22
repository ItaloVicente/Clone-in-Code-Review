package org.eclipse.e4.ui.tests.model.test.impl;

import org.eclipse.e4.ui.model.application.commands.impl.CommandsPackageImpl;
import org.eclipse.e4.ui.model.application.impl.ApplicationPackageImpl;
import org.eclipse.e4.ui.model.application.ui.impl.UiPackageImpl;
import org.eclipse.e4.ui.tests.model.test.MTestFactory;
import org.eclipse.e4.ui.tests.model.test.MTestHarness;
import org.eclipse.e4.ui.tests.model.test.MTestPackage;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;

public class TestPackageImpl extends EPackageImpl implements MTestPackage {
	private EClass testHarnessEClass = null;

	private TestPackageImpl() {
		super(eNS_URI, MTestFactory.eINSTANCE);
	}

	private static boolean isInited = false;

	public static MTestPackage init() {
		if (isInited) return (MTestPackage)EPackage.Registry.INSTANCE.getEPackage(MTestPackage.eNS_URI);

		TestPackageImpl theTestPackage = (TestPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof TestPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new TestPackageImpl());

		isInited = true;

		ApplicationPackageImpl.eINSTANCE.eClass();

		theTestPackage.createPackageContents();

		theTestPackage.initializePackageContents();

		theTestPackage.freeze();

  
		EPackage.Registry.INSTANCE.put(MTestPackage.eNS_URI, theTestPackage);
		return theTestPackage;
	}

	public EClass getTestHarness() {
		return testHarnessEClass;
	}

	public MTestFactory getTestFactory() {
		return (MTestFactory)getEFactoryInstance();
	}

	private boolean isCreated = false;

	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		testHarnessEClass = createEClass(TEST_HARNESS);
	}

	private boolean isInitialized = false;

	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		ApplicationPackageImpl theApplicationPackage = (ApplicationPackageImpl)EPackage.Registry.INSTANCE.getEPackage(ApplicationPackageImpl.eNS_URI);
		CommandsPackageImpl theCommandsPackage = (CommandsPackageImpl)EPackage.Registry.INSTANCE.getEPackage(CommandsPackageImpl.eNS_URI);
		UiPackageImpl theUiPackage = (UiPackageImpl)EPackage.Registry.INSTANCE.getEPackage(UiPackageImpl.eNS_URI);



		EGenericType g1 = createEGenericType(theApplicationPackage.getApplicationElement());
		testHarnessEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theCommandsPackage.getCommand());
		testHarnessEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theUiPackage.getContext());
		testHarnessEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theApplicationPackage.getContribution());
		testHarnessEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theUiPackage.getElementContainer());
		EGenericType g2 = createEGenericType(theUiPackage.getUIElement());
		g1.getETypeArguments().add(g2);
		testHarnessEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theCommandsPackage.getParameter());
		testHarnessEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theUiPackage.getInput());
		testHarnessEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theUiPackage.getUILabel());
		testHarnessEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theUiPackage.getDirtyable());
		testHarnessEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theUiPackage.getSnippetContainer());
		testHarnessEClass.getEGenericSuperTypes().add(g1);

		initEClass(testHarnessEClass, MTestHarness.class, "TestHarness", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		createResource(eNS_URI);
	}

} // TestPackageImpl
