		initEAttribute(getItem_Enabled(), ecorePackage.getEBoolean(), "enabled", "true", 0, 1, MItem.class, //$NON-NLS-1$//$NON-NLS-2$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getItem_Selected(), ecorePackage.getEBoolean(), "selected", null, 0, 1, MItem.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getItem_Type(), this.getItemType(), "type", null, 1, 1, MItem.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getItem__UpdateLocalization(), null, "updateLocalization", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(handledItemEClass, MHandledItem.class, "HandledItem", IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getHandledItem_Command(), theCommandsPackage.getCommand(), null, "command", null, 0, 1, //$NON-NLS-1$
				MHandledItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHandledItem_WbCommand(), theCommandsPackage.getParameterizedCommand(), "wbCommand", null, 0, //$NON-NLS-1$
				1, MHandledItem.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getHandledItem_Parameters(), theCommandsPackage.getParameter(), null, "parameters", null, 0, -1, //$NON-NLS-1$
				MHandledItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(menuElementEClass, MMenuElement.class, "MenuElement", IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMenuElement_Mnemonics(), ecorePackage.getEString(), "mnemonics", null, 0, 1, //$NON-NLS-1$
				MMenuElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEOperation(getMenuElement__GetLocalizedMnemonics(), ecorePackage.getEString(), "getLocalizedMnemonics", 0, //$NON-NLS-1$
				1, IS_UNIQUE, IS_ORDERED);

		initEOperation(getMenuElement__UpdateLocalization(), null, "updateLocalization", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(menuItemEClass, MMenuItem.class, "MenuItem", IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getMenuItem__UpdateLocalization(), null, "updateLocalization", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(menuSeparatorEClass, MMenuSeparator.class, "MenuSeparator", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
