
package org.eclipse.e4.ui.internal.workbench;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.eclipse.e4.ui.model.application.MApplicationElement;
import org.eclipse.e4.ui.model.application.impl.ApplicationPackageImpl;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.osgi.service.log.LogService;

final class GenericMApplicationElementFactoryImpl {

	private final MApplicationElementClassToEClass emfGeneratedPackages;

	GenericMApplicationElementFactoryImpl(IExtensionRegistry extensionRegistry) {
		if (extensionRegistry == null)
			throw new NullPointerException("No ExtensionRegistry given!"); //$NON-NLS-1$

		emfGeneratedPackages = new MApplicationElementClassToEClass();

		extensionRegistry.addListener(emfGeneratedPackages,
				MApplicationElementClassToEClass.EP_GENERATED_PACKAGE);
		emfGeneratedPackages.initialize(extensionRegistry);
	}

	public EObject createEObject(Class<? extends MApplicationElement> clazz) {
		EClass eClass = emfGeneratedPackages.getEClass(clazz);
		if (eClass != null) {
			checkDeprecation(eClass);
			return EcoreUtil.create(eClass);
		}

		return null;
	}

	private static void checkDeprecation(EClass eClass) {
		if (eClass == null)
			return;

		EAnnotation deprecated = eClass
				.getEAnnotation("http://www.eclipse.org/ui/2010/UIModel/application/deprecated"); //$NON-NLS-1$
		if (deprecated != null) {
			StringBuilder sb = new StringBuilder("The element '").append(eClass.getInstanceTypeName()).append("' is already deprecated!"); //$NON-NLS-1$ //$NON-NLS-2$

			String since = deprecated.getDetails().get("since"); //$NON-NLS-1$
			if (since != null) {
				sb.append(" (since version: ").append(since).append(')'); //$NON-NLS-1$
			}

			Activator.log(LogService.LOG_WARNING, sb.toString());
		}
	}

	private static final class MApplicationElementClassToEClass implements IRegistryEventListener {

		public static final String EP_GENERATED_PACKAGE = "org.eclipse.emf.ecore.generated_package"; //$NON-NLS-1$

		private static final String CONFIG_ELEMENT_NAME = "package"; //$NON-NLS-1$

		private static final String CONFIG_ATTR_EPACKAGE_URI = "uri"; //$NON-NLS-1$

		private final ConcurrentMap<Class<? extends MApplicationElement>, EClass> classToEClass = new ConcurrentHashMap<Class<? extends MApplicationElement>, EClass>();

		private final ConcurrentMap<IExtension, List<Class<? extends MApplicationElement>>> registeredClasses = new ConcurrentHashMap<IExtension, List<Class<? extends MApplicationElement>>>();

		private final EClass mApplicationElementEClass = ApplicationPackageImpl.eINSTANCE
				.getApplicationElement();

		void initialize(IExtensionRegistry extensionRegistry) {
			if (extensionRegistry == null) { // just for safety's sake
				throw new IllegalArgumentException("No ExtensionRegistry given!"); //$NON-NLS-1$
			}

			IExtensionPoint epGeneratedPackage = extensionRegistry
					.getExtensionPoint(EP_GENERATED_PACKAGE);
			if (epGeneratedPackage != null) {
				added(epGeneratedPackage.getExtensions());
			}
		}

		public EClass getEClass(Class<? extends MApplicationElement> elementType) {
			return classToEClass.get(elementType);
		}

		public void added(IExtension[] extensions) {
			for (IExtension extension : extensions) {
				List<Class<? extends MApplicationElement>> elementsToCleanup = addToMapping(extension
						.getConfigurationElements());

				if (elementsToCleanup != null) {
					registeredClasses.put(extension, elementsToCleanup);
				}
			}
		}

		public void removed(IExtension[] extensions) {
			for (IExtension extension : extensions) {
				List<Class<? extends MApplicationElement>> modelClassesToRemove = registeredClasses
						.remove(extension);

				if (modelClassesToRemove != null) {
					for (Class<? extends MApplicationElement> modelClass : modelClassesToRemove) {
						classToEClass.remove(modelClass);
					}
				}
			}
		}

		public void added(IExtensionPoint[] extensionPoints) {
		}

		public void removed(IExtensionPoint[] extensionPoints) {
		}

		private List<Class<? extends MApplicationElement>> addToMapping(
				IConfigurationElement[] configurationElements) {
			if (configurationElements == null) {
				return null;
			}

			List<Class<? extends MApplicationElement>> allMappedEntried = new ArrayList<Class<? extends MApplicationElement>>();

			for (IConfigurationElement configElement : configurationElements) {
				if (configElement.getName().equals(CONFIG_ELEMENT_NAME)) {
					String emfNsURI = configElement.getAttribute(CONFIG_ATTR_EPACKAGE_URI);

					EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(emfNsURI);

					Map<Class<? extends MApplicationElement>, EClass> mapping = buildMapping(ePackage);

					if (mapping != null) {
						for (Map.Entry<Class<? extends MApplicationElement>, EClass> entry : mapping
								.entrySet()) {

							if (classToEClass.putIfAbsent(entry.getKey(), entry.getValue()) == null) {
								allMappedEntried.add(entry.getKey());
							}
						}
					}
				}
			}

			return allMappedEntried.isEmpty() ? null : allMappedEntried;
		}

		private final Map<Class<? extends MApplicationElement>, EClass> buildMapping(
				EPackage ePackage) {
			if (ePackage == null)
				return null;

			List<EClassifier> eClassifiers = ePackage.getEClassifiers();
			Map<Class<? extends MApplicationElement>, EClass> mapping = new HashMap<Class<? extends MApplicationElement>, EClass>();

			for (EClassifier eClassifier : eClassifiers) {
				if (eClassifier instanceof EClass) {
					EClass eClass = (EClass) eClassifier;

					if (mApplicationElementEClass.isSuperTypeOf(eClass) && !eClass.isAbstract()
							&& !eClass.isInterface()) {
						@SuppressWarnings("unchecked")
						Class<? extends MApplicationElement> instanceClass = (Class<? extends MApplicationElement>) eClass
								.getInstanceClass();

						if (!instanceClass.equals(Map.Entry.class)) {
							EClass previousEntry = mapping.put(instanceClass, eClass);

							if (previousEntry != null) {
								Activator
										.log(LogService.LOG_WARNING,
												instanceClass
														+ " is mapped to multiple EClasses (" + eClass.getName() + ", " + previousEntry.getName() + ")!"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
							}
						}
					}

				}
			}

			return mapping.isEmpty() ? null : mapping;
		}
	}
}
