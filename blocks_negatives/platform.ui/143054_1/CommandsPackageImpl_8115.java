		initEReference(getBindings_BindingContexts(), this.getBindingContext(), null, "bindingContexts", null, 0, -1, MBindings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(bindingContextEClass, MBindingContext.class, "BindingContext", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getBindingContext_Name(), ecorePackage.getEString(), "name", null, 0, 1, MBindingContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getBindingContext_Description(), ecorePackage.getEString(), "description", null, 0, 1, MBindingContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getBindingContext_Children(), this.getBindingContext(), null, "children", null, 0, -1, MBindingContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(bindingTableEClass, MBindingTable.class, "BindingTable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getBindingTable_Bindings(), this.getKeyBinding(), null, "bindings", null, 0, -1, MBindingTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getBindingTable_BindingContext(), this.getBindingContext(), null, "bindingContext", null, 1, 1, MBindingTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
