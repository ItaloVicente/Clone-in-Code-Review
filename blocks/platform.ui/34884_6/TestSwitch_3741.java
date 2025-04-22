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
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

public class TestAdapterFactory extends AdapterFactoryImpl {
	protected static MTestPackage modelPackage;

	public TestAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = MTestPackage.eINSTANCE;
		}
	}

	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	protected TestSwitch<Adapter> modelSwitch = new TestSwitch<Adapter>() {
		@Override
		public Adapter caseTestHarness(MTestHarness object) {
			return createTestHarnessAdapter();
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
		public Adapter caseCommand(MCommand object) {
			return createCommandAdapter();
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
		public Adapter caseUIElement(MUIElement object) {
			return createUIElementAdapter();
		}

		@Override
		public <T extends MUIElement> Adapter caseElementContainer(
				MElementContainer<T> object) {
			return createElementContainerAdapter();
		}

		@Override
		public Adapter caseParameter(MParameter object) {
			return createParameterAdapter();
		}

		@Override
		public Adapter caseInput(MInput object) {
			return createInputAdapter();
		}

		@Override
		public Adapter caseUILabel(MUILabel object) {
			return createUILabelAdapter();
		}

		@Override
		public Adapter caseDirtyable(MDirtyable object) {
			return createDirtyableAdapter();
		}

		@Override
		public Adapter caseSnippetContainer(MSnippetContainer object) {
			return createSnippetContainerAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	public Adapter createTestHarnessAdapter() {
		return null;
	}

	public Adapter createApplicationElementAdapter() {
		return null;
	}

	public Adapter createLocalizableAdapter() {
		return null;
	}

	public Adapter createCommandAdapter() {
		return null;
	}

	public Adapter createContextAdapter() {
		return null;
	}

	public Adapter createContributionAdapter() {
		return null;
	}

	public Adapter createUIElementAdapter() {
		return null;
	}

	public Adapter createElementContainerAdapter() {
		return null;
	}

	public Adapter createParameterAdapter() {
		return null;
	}

	public Adapter createInputAdapter() {
		return null;
	}

	public Adapter createUILabelAdapter() {
		return null;
	}

	public Adapter createDirtyableAdapter() {
		return null;
	}

	public Adapter createSnippetContainerAdapter() {
		return null;
	}

	public Adapter createEObjectAdapter() {
		return null;
	}

} // TestAdapterFactory
