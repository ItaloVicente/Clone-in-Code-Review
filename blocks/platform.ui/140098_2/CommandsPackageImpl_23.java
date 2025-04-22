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
