	private static void registerJXPathPropertyHandlers() {
		EPackage[] packages = new EPackage[] { EcorePackage.eINSTANCE, ApplicationPackageImpl.eINSTANCE,
				CommandsPackageImpl.eINSTANCE, BasicPackageImpl.eINSTANCE, UiPackageImpl.eINSTANCE,
				AdvancedPackageImpl.eINSTANCE,
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
