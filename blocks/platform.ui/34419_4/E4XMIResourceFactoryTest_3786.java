package org.eclipse.e4.ui.tests.model.test.util;

import org.eclipse.e4.ui.model.application.MApplicationElement;
import org.eclipse.e4.ui.model.application.MContribution;
import org.eclipse.e4.ui.model.application.commands.MCommand;
import org.eclipse.e4.ui.model.application.commands.MParameter;
import org.eclipse.e4.ui.model.application.ui.MContext;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MInput;
import org.eclipse.e4.ui.model.application.ui.MLocalizable;
import org.eclipse.e4.ui.model.application.ui.MSnippetContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.MUILabel;
import org.eclipse.e4.ui.tests.model.test.MTestHarness;
import org.eclipse.e4.ui.tests.model.test.MTestPackage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

public class TestSwitch<T1> extends Switch<T1> {
	protected static MTestPackage modelPackage;

	public TestSwitch() {
		if (modelPackage == null) {
			modelPackage = MTestPackage.eINSTANCE;
		}
	}

	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	@Override
	protected T1 doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
		case MTestPackage.TEST_HARNESS: {
			MTestHarness testHarness = (MTestHarness) theEObject;
			T1 result = caseTestHarness(testHarness);
			if (result == null)
				result = caseCommand(testHarness);
			if (result == null)
				result = caseContext(testHarness);
			if (result == null)
				result = caseContribution(testHarness);
			if (result == null)
				result = caseElementContainer(testHarness);
			if (result == null)
				result = caseParameter(testHarness);
			if (result == null)
				result = caseInput(testHarness);
			if (result == null)
				result = caseUILabel(testHarness);
			if (result == null)
				result = caseDirtyable(testHarness);
			if (result == null)
				result = caseSnippetContainer(testHarness);
			if (result == null)
				result = caseUIElement(testHarness);
			if (result == null)
				result = caseApplicationElement(testHarness);
			if (result == null)
				result = caseLocalizable(testHarness);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	public T1 caseTestHarness(MTestHarness object) {
		return null;
	}

	public T1 caseApplicationElement(MApplicationElement object) {
		return null;
	}

	public T1 caseLocalizable(MLocalizable object) {
		return null;
	}

	public T1 caseCommand(MCommand object) {
		return null;
	}

	public T1 caseContext(MContext object) {
		return null;
	}

	public T1 caseContribution(MContribution object) {
		return null;
	}

	public T1 caseUIElement(MUIElement object) {
		return null;
	}

	public <T extends MUIElement> T1 caseElementContainer(
			MElementContainer<T> object) {
		return null;
	}

	public T1 caseParameter(MParameter object) {
		return null;
	}

	public T1 caseInput(MInput object) {
		return null;
	}

	public T1 caseUILabel(MUILabel object) {
		return null;
	}

	public T1 caseDirtyable(MDirtyable object) {
		return null;
	}

	public T1 caseSnippetContainer(MSnippetContainer object) {
		return null;
	}

	@Override
	public T1 defaultCase(EObject object) {
		return null;
	}

} // TestSwitch
