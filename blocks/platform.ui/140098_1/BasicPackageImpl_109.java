		initEReference(getPart_Menus(), theMenuPackage.getMenu(), null, "menus", null, 0, -1, MPart.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPart_Toolbar(), theMenuPackage.getToolBar(), null, "toolbar", null, 0, 1, MPart.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPart_Closeable(), ecorePackage.getEBoolean(), "closeable", "false", 0, 1, MPart.class, //$NON-NLS-1$//$NON-NLS-2$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPart_Description(), ecorePackage.getEString(), "description", null, 0, 1, MPart.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPart_LocalizedDescription(), ecorePackage.getEString(), "localizedDescription", null, 0, 1, //$NON-NLS-1$
				MPart.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED,
				IS_ORDERED);
		initEReference(getPart_TrimBars(), this.getTrimBar(), null, "trimBars", null, 0, -1, MPart.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
