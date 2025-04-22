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
