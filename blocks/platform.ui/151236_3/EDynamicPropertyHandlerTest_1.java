package org.eclipse.e4.ui.model;

import org.apache.commons.jxpath.JXPathIntrospector;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.emf.internal.xpath.helper.EDynamicPropertyHandler;
import org.eclipse.e4.ui.model.application.commands.impl.CommandsPackageImpl;
import org.eclipse.e4.ui.model.application.descriptor.basic.impl.BasicPackageImpl;
import org.eclipse.e4.ui.model.application.impl.ApplicationPackageImpl;
import org.eclipse.e4.ui.model.application.ui.advanced.impl.AdvancedPackageImpl;
import org.eclipse.e4.ui.model.application.ui.impl.UiPackageImpl;
import org.eclipse.e4.ui.model.application.ui.menu.impl.MenuPackageImpl;
import org.eclipse.e4.ui.model.fragment.impl.FragmentPackageImpl;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	@Override
	public void start(BundleContext context) {
		new Job("Register dynamic property handlers for workbench model") {
			{
				setSystem(true);
			}

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				registerJXPathPropertyHandlers();
				return Status.OK_STATUS;
			}
		}.schedule();
	}

	private void registerJXPathPropertyHandlers() {
		EPackage[] packages = new EPackage[] { ApplicationPackageImpl.eINSTANCE, CommandsPackageImpl.eINSTANCE,
				BasicPackageImpl.eINSTANCE, UiPackageImpl.eINSTANCE, AdvancedPackageImpl.eINSTANCE,
				org.eclipse.e4.ui.model.application.ui.basic.impl.BasicPackageImpl.eINSTANCE, MenuPackageImpl.eINSTANCE,
				FragmentPackageImpl.eINSTANCE };

		for (EPackage ePackage : packages) {
			for (EClassifier eClassifier : ePackage.getEClassifiers()) {
				if (!(eClassifier instanceof EClass)) {
					continue;
				}
				EClass eClass = (EClass) eClassifier;
				if (eClass.isAbstract()) {
					continue;
				}
				EFactory eFactory = EPackage.Registry.INSTANCE.getEFactory(ePackage.getNsURI());
				Class<? extends EObject> implClass = eFactory.create(eClass).getClass();
				JXPathIntrospector.registerDynamicClass(implClass, EDynamicPropertyHandler.class);
			}
		}
	}

	@Override
	public void stop(BundleContext context) throws Exception {
	}

}
