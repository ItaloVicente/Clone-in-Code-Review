package org.eclipse.e4.ui.tests.model.test;

import org.eclipse.emf.ecore.EFactory;

public interface MTestFactory extends EFactory {
	MTestFactory eINSTANCE = org.eclipse.e4.ui.tests.model.test.impl.TestFactoryImpl.init();

	MTestHarness createTestHarness();

	MTestPackage getTestPackage();

} //MTestFactory
