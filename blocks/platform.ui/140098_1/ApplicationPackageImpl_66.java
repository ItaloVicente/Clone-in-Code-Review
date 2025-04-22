		initEClass(stringToStringMapEClass, Map.Entry.class, "StringToStringMap", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				!IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringToStringMap_Key(), ecorePackage.getEString(), "key", null, 0, 1, Map.Entry.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStringToStringMap_Value(), ecorePackage.getEString(), "value", null, 0, 1, Map.Entry.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(applicationEClass, MApplication.class, "Application", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getApplication_Commands(), theCommandsPackage.getCommand(), null, "commands", null, 0, -1, //$NON-NLS-1$
				MApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getApplication_Addons(), this.getAddon(), null, "addons", null, 0, -1, MApplication.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getApplication_Categories(), theCommandsPackage.getCategory(), null, "categories", null, 0, -1, //$NON-NLS-1$
				MApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getApplication_Dialogs(), theBasicPackage.getDialog(), null, "dialogs", null, 0, -1, //$NON-NLS-1$
				MApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = initEOperation(getApplication__GetCommand__String(), theCommandsPackage.getCommand(),
				"getCommand", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
