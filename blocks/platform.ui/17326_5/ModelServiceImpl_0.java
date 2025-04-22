
package org.eclipse.e4.ui.internal.workbench;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.eclipse.e4.ui.workbench.modeling.EClassProvider;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

public class EClassProviderImpl implements EClassProvider {

	private IExtensionRegistry extensionRegistry;

	private volatile EmfGeneratedPackages emfGeneratedPackages;
	
	public void bindExtensionRegistry(IExtensionRegistry extensionRegistry) {
		this.extensionRegistry = extensionRegistry;
	}

	public void activate() {
		if (extensionRegistry == null) { // just for safety's sake
			throw new IllegalStateException("Can't activate before ExtensionRegistry is bound!"); //$NON-NLS-1$
		}
		
		if( emfGeneratedPackages != null ){ // just for safety's sake
			throw new IllegalStateException("Can't activate because it's already activated!"); //$NON-NLS-1$
		}
		
		emfGeneratedPackages = new EmfGeneratedPackages();
		extensionRegistry.addListener(emfGeneratedPackages, EmfGeneratedPackages.EP_GENERATED_PACKAGE);
		emfGeneratedPackages.initialize(extensionRegistry);
	}

	public void deactivate() {
		EmfGeneratedPackages emfPackages = emfGeneratedPackages; // keep local reference
		emfGeneratedPackages = null; // clear global one
		
		if (emfPackages != null) {
			extensionRegistry.removeListener(emfGeneratedPackages);
			emfPackages.dispose();
		}
	}

	public EClass getEClass(Class<?> clazz) {
		EmfGeneratedPackages emfPackages = emfGeneratedPackages; // use a local reference to minimize the thread-synchronization at this important step
		
		if (emfPackages != null) {
			String ePackageNameSpace = emfPackages.getEPackageNameSpaceURI(clazz);

			if (ePackageNameSpace != null) {
				EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(ePackageNameSpace);

				if (ePackage != null) {
					EClassifier eClass = ePackage.getEClassifier(clazz.getSimpleName());

					if (eClass == null) {
						for (EClassifier eClassifier : ePackage.getEClassifiers()) {
							if (clazz.equals(eClassifier.getInstanceClass())) {
								eClass = eClassifier;
								break; // exit loop, because the element was found
							}
						}
					}

					if (eClass instanceof EClass) {
						return (EClass) eClass;
					}
				}
			}
		}
		return null;
	}

	private static final class EmfGeneratedPackages implements IRegistryEventListener {

		public static final String EP_GENERATED_PACKAGE = "org.eclipse.emf.ecore.generated_package"; //$NON-NLS-1$

		private static final String CONFIG_ELEMENT_NAME = "package"; //$NON-NLS-1$
		private static final String CONFIG_ATTR_EPACKAGE_URI = "uri"; //$NON-NLS-1$
		private static final String CONFIG_ATTR_EPACKAGE_IMPL = "class"; //$NON-NLS-1$

		private final ConcurrentMap<String, String> packageToEPackageNampeSpace = new ConcurrentHashMap<String, String>();
		
		private final ConcurrentMap<IExtension, List<String>> registeredPackages = new ConcurrentHashMap<IExtension, List<String>>();

		public void initialize(IExtensionRegistry extensionRegistry) {
			if (extensionRegistry == null) {
				throw new IllegalArgumentException("No ExtensionRegistry given!"); //$NON-NLS-1$
			}
			
			IExtensionPoint epGeneratedPackage = extensionRegistry.getExtensionPoint(EP_GENERATED_PACKAGE);
			if( epGeneratedPackage != null ){
				added(epGeneratedPackage.getExtensions());
			}
		}

		public void dispose() {
			packageToEPackageNampeSpace.clear();
			registeredPackages.clear();
		}

		public String getEPackageNameSpaceURI(Class<?> clazz) {
			if (clazz != null) {
				return packageToEPackageNampeSpace.get(clazz.getPackage().getName()); // the package-name of the class is the entry-point to the mapping
			}

			return null;
		}
		
		public void added(IExtension[] extensions) {
			for( IExtension extension : extensions ){
				List<String> elementsToCleanup = addToMapping(extension.getConfigurationElements());
				
				if( elementsToCleanup != null ){
					registeredPackages.put(extension, elementsToCleanup); // keep the list of registered packages per Extension to remove them in the #remove(IExtension[]) method 
				}
			}
		}

		public void removed(IExtension[] extensions) {
			for( IExtension extension : extensions ){
				List<String> modelPackageNamesToRemove = registeredPackages.get(extension);
				
				if( modelPackageNamesToRemove != null ){
					for( String modelPackageName : modelPackageNamesToRemove ){
						packageToEPackageNampeSpace.remove(modelPackageName);
					}
					
					registeredPackages.remove(extension);
				}				 
			}
		}
		
		public void added(IExtensionPoint[] extensionPoints){
		}
		
		public void removed(IExtensionPoint[] extensionPoints){
		}

		private List<String> addToMapping(IConfigurationElement[] configurationElements) {
			if (configurationElements == null) {
				return null;
			}
			
			List<String> allMappedEntried = new ArrayList<String>();

			for (IConfigurationElement configElement : configurationElements) {
				if (configElement.getName().equals(CONFIG_ELEMENT_NAME)) {
					String modelPackageName = extractPackage(configElement.getAttribute(CONFIG_ATTR_EPACKAGE_IMPL));

					if (modelPackageName != null) {
						if( packageToEPackageNampeSpace.putIfAbsent(modelPackageName, configElement.getAttribute(CONFIG_ATTR_EPACKAGE_URI) ) == null ){
							allMappedEntried.add(modelPackageName);
						}
					}
				}
			}
			
			return allMappedEntried.isEmpty() ? null : allMappedEntried;
		}

		private static String extractPackage(String ePackageImpl) {
			if (ePackageImpl != null) {
				int lastDot = ePackageImpl.lastIndexOf('.');

				if (lastDot >= 0) {
					String packageName = ePackageImpl.substring(0, lastDot); // this should be enough for normal EMF models

					if (packageName.endsWith(".impl")) { // default implementation name generated by EMF if "Suppress EMF Metadata" is activated //$NON-NLS-1$
						return packageName.substring(0, packageName.length() - 5); // 5 == ".impl".length()
					}

					return packageName;
				}
			}

			return null;
		}
	}
}
