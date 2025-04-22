		initEReference(getWindow_MainMenu(), theMenuPackage.getMenu(), null, "mainMenu", null, 0, 1, MWindow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getWindow_X(), ecorePackage.getEInt(), "x", "-2147483648", 0, 1, MWindow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getWindow_Y(), ecorePackage.getEInt(), "y", "-2147483648", 0, 1, MWindow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getWindow_Width(), ecorePackage.getEInt(), "width", "-1", 0, 1, MWindow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getWindow_Height(), ecorePackage.getEInt(), "height", "-1", 0, 1, MWindow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getWindow_Windows(), this.getWindow(), null, "windows", null, 0, -1, MWindow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getWindow_SharedElements(), theUiPackage.getUIElement(), null, "sharedElements", null, 0, -1, MWindow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(trimmedWindowEClass, MTrimmedWindow.class, "TrimmedWindow", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getTrimmedWindow_TrimBars(), this.getTrimBar(), null, "trimBars", null, 0, -1, MTrimmedWindow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(trimElementEClass, MTrimElement.class, "TrimElement", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(partSashContainerElementEClass, MPartSashContainerElement.class, "PartSashContainerElement", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(windowElementEClass, MWindowElement.class, "WindowElement", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
