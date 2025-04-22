		initEClass(placeholderEClass, MPlaceholder.class, "Placeholder", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPlaceholder_Ref(), theUiPackage.getUIElement(), null, "ref", null, 1, 1, MPlaceholder.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPlaceholder_Closeable(), ecorePackage.getEBoolean(), "closeable", "false", 0, 1, //$NON-NLS-1$//$NON-NLS-2$
				MPlaceholder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(perspectiveEClass, MPerspective.class, "Perspective", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPerspective_Windows(), theBasicPackage.getWindow(), null, "windows", null, 0, -1, //$NON-NLS-1$
				MPerspective.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPerspective_TrimBars(), theBasicPackage.getTrimBar(), null, "trimBars", null, 0, -1, //$NON-NLS-1$
				MPerspective.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
