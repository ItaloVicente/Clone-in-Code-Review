		initEAttribute(getContext_Context(), theApplicationPackage.getIEclipseContext(), "context", null, 0, 1, MContext.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getContext_Variables(), ecorePackage.getEString(), "variables", null, 0, -1, MContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getContext_Properties(), theApplicationPackage.getStringToStringMap(), null, "properties", null, 0, -1, MContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(dirtyableEClass, MDirtyable.class, "Dirtyable", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getDirtyable_Dirty(), ecorePackage.getEBoolean(), "dirty", null, 0, 1, MDirtyable.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
