		initEAttribute(getCommand_CommandName(), ecorePackage.getEString(), "commandName", null, 0, 1, MCommand.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCommand_Description(), ecorePackage.getEString(), "description", null, 0, 1, MCommand.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCommand_Parameters(), this.getCommandParameter(), null, "parameters", null, 0, -1, //$NON-NLS-1$
				MCommand.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCommand_Category(), this.getCategory(), null, "category", null, 0, 1, MCommand.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCommand_LocalizedCommandName(), ecorePackage.getEString(), "localizedCommandName", null, 0, 1, //$NON-NLS-1$
				MCommand.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				IS_DERIVED, IS_ORDERED);
		initEAttribute(getCommand_LocalizedDescription(), ecorePackage.getEString(), "localizedDescription", null, 0, 1, //$NON-NLS-1$
				MCommand.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				IS_DERIVED, IS_ORDERED);

		initEOperation(getCommand__UpdateLocalization(), null, "updateLocalization", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(commandParameterEClass, MCommandParameter.class, "CommandParameter", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCommandParameter_Name(), ecorePackage.getEString(), "name", null, 1, 1, //$NON-NLS-1$
				MCommandParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getCommandParameter_TypeId(), ecorePackage.getEString(), "typeId", null, 0, 1, //$NON-NLS-1$
				MCommandParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getCommandParameter_Optional(), ecorePackage.getEBoolean(), "optional", "true", 0, 1, //$NON-NLS-1$//$NON-NLS-2$
				MCommandParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
