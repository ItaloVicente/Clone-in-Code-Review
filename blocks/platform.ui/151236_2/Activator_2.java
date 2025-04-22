
		for (EClassifier eClassifier : ApplicationPackageImpl.eINSTANCE.getEClassifiers()) {
			if (!(eClassifier instanceof EClass)) {
				continue;
			}
			EClass eClass = (EClass) eClassifier;
			if (eClass.isAbstract()) {
				continue;
			}
			Class<? extends EObject> implClass = ApplicationFactoryImpl.eINSTANCE.create(eClass)
					.getClass();
			JXPathIntrospector.registerDynamicClass(implClass, EDynamicPropertyHandler.class);
		}
