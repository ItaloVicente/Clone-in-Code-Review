		initEClass(modelFragmentsEClass, MModelFragments.class, "ModelFragments", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getModelFragments_Imports(), theApplicationPackage.getApplicationElement(), null, "imports", //$NON-NLS-1$
				null, 0, -1, MModelFragments.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getModelFragments_Fragments(), this.getModelFragment(), null, "fragments", null, 0, -1, //$NON-NLS-1$
				MModelFragments.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(modelFragmentEClass, MModelFragment.class, "ModelFragment", IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getModelFragment_Elements(), theApplicationPackage.getApplicationElement(), null, "elements", //$NON-NLS-1$
				null, 0, -1, MModelFragment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = initEOperation(getModelFragment__Merge__MApplication(),
				theApplicationPackage.getApplicationElement(), "merge", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
