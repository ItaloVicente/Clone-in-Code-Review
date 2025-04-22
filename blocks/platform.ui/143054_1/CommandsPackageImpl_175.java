		initEReference(getHandler_Command(), this.getCommand(), null, "command", null, 1, 1, MHandler.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(handlerContainerEClass, MHandlerContainer.class, "HandlerContainer", IS_ABSTRACT, IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getHandlerContainer_Handlers(), this.getHandler(), null, "handlers", null, 0, -1, //$NON-NLS-1$
				MHandlerContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(keyBindingEClass, MKeyBinding.class, "KeyBinding", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getKeyBinding_Command(), this.getCommand(), null, "command", null, 1, 1, MKeyBinding.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getKeyBinding_Parameters(), this.getParameter(), null, "parameters", null, 0, -1, //$NON-NLS-1$
				MKeyBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(keySequenceEClass, MKeySequence.class, "KeySequence", IS_ABSTRACT, IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getKeySequence_KeySequence(), ecorePackage.getEString(), "keySequence", null, 1, 1, //$NON-NLS-1$
				MKeySequence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(parameterEClass, MParameter.class, "Parameter", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getParameter_Name(), ecorePackage.getEString(), "name", null, 0, 1, MParameter.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getParameter_Value(), ecorePackage.getEString(), "value", null, 0, 1, MParameter.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(categoryEClass, MCategory.class, "Category", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCategory_Name(), ecorePackage.getEString(), "name", null, 1, 1, MCategory.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCategory_Description(), ecorePackage.getEString(), "description", null, 0, 1, MCategory.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCategory_LocalizedName(), ecorePackage.getEString(), "localizedName", "", 0, 1, //$NON-NLS-1$//$NON-NLS-2$
				MCategory.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				IS_DERIVED, IS_ORDERED);
		initEAttribute(getCategory_LocalizedDescription(), ecorePackage.getEString(), "localizedDescription", null, 0, //$NON-NLS-1$
				1, MCategory.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				IS_DERIVED, IS_ORDERED);

		initEOperation(getCategory__UpdateLocalization(), null, "updateLocalization", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
