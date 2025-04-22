		initEReference(getWindow_MainMenu(), theMenuPackage.getMenu(), null, "mainMenu", null, 0, 1, MWindow.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWindow_X(), ecorePackage.getEInt(), "x", "-2147483648", 0, 1, MWindow.class, !IS_TRANSIENT, //$NON-NLS-1$//$NON-NLS-2$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWindow_Y(), ecorePackage.getEInt(), "y", "-2147483648", 0, 1, MWindow.class, !IS_TRANSIENT, //$NON-NLS-1$//$NON-NLS-2$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWindow_Width(), ecorePackage.getEInt(), "width", "-1", 0, 1, MWindow.class, !IS_TRANSIENT, //$NON-NLS-1$//$NON-NLS-2$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWindow_Height(), ecorePackage.getEInt(), "height", "-1", 0, 1, MWindow.class, !IS_TRANSIENT, //$NON-NLS-1$//$NON-NLS-2$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getWindow_Windows(), this.getWindow(), null, "windows", null, 0, -1, MWindow.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getWindow_SharedElements(), theUiPackage.getUIElement(), null, "sharedElements", null, 0, -1, //$NON-NLS-1$
				MWindow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
