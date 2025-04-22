		initEAttribute(getContext_Context(), theApplicationPackage.getIEclipseContext(), "context", null, 0, 1, //$NON-NLS-1$
				MContext.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				IS_DERIVED, IS_ORDERED);
		initEAttribute(getContext_Variables(), ecorePackage.getEString(), "variables", null, 0, -1, MContext.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				!IS_ORDERED);
		initEReference(getContext_Properties(), theApplicationPackage.getStringToStringMap(), null, "properties", null, //$NON-NLS-1$
				0, -1, MContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dirtyableEClass, MDirtyable.class, "Dirtyable", IS_ABSTRACT, IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDirtyable_Dirty(), ecorePackage.getEBoolean(), "dirty", null, 0, 1, MDirtyable.class, //$NON-NLS-1$
				IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
