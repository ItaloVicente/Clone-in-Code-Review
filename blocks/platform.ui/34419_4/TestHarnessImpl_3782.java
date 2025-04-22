package org.eclipse.e4.ui.tests.model.test.impl;

import org.eclipse.e4.ui.tests.model.test.MTestFactory;
import org.eclipse.e4.ui.tests.model.test.MTestHarness;
import org.eclipse.e4.ui.tests.model.test.MTestPackage;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

public class TestFactoryImpl extends EFactoryImpl implements MTestFactory {
	public static MTestFactory init() {
		try {
			MTestFactory theTestFactory = (MTestFactory) EPackage.Registry.INSTANCE
					.getEFactory(MTestPackage.eNS_URI);
			if (theTestFactory != null) {
				return theTestFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TestFactoryImpl();
	}

	public TestFactoryImpl() {
		super();
	}

	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case MTestPackage.TEST_HARNESS:
			return (EObject) createTestHarness();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName()
					+ "' is not a valid classifier");
		}
	}

	public MTestHarness createTestHarness() {
		TestHarnessImpl testHarness = new TestHarnessImpl();
		return testHarness;
	}

	public MTestPackage getTestPackage() {
		return (MTestPackage) getEPackage();
	}

	@Deprecated
	public static MTestPackage getPackage() {
		return MTestPackage.eINSTANCE;
	}

} // TestFactoryImpl
