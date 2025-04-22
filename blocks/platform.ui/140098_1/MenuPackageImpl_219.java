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
