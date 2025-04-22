		AdvancedPackageImpl theAdvancedPackage = (AdvancedPackageImpl) (registeredPackage instanceof AdvancedPackageImpl
				? registeredPackage
				: AdvancedPackageImpl.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE
				.getEPackage(org.eclipse.e4.ui.model.application.descriptor.basic.impl.BasicPackageImpl.eNS_URI);
		org.eclipse.e4.ui.model.application.descriptor.basic.impl.BasicPackageImpl theBasicPackage_1 = (org.eclipse.e4.ui.model.application.descriptor.basic.impl.BasicPackageImpl) (registeredPackage instanceof org.eclipse.e4.ui.model.application.descriptor.basic.impl.BasicPackageImpl
				? registeredPackage
				: org.eclipse.e4.ui.model.application.descriptor.basic.impl.BasicPackageImpl.eINSTANCE);
