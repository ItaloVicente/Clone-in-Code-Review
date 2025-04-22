		initEClass(bindingTableContainerEClass, MBindingTableContainer.class, "BindingTableContainer", IS_ABSTRACT, //$NON-NLS-1$
				IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBindingTableContainer_BindingTables(), this.getBindingTable(), null, "bindingTables", null, 0, //$NON-NLS-1$
				-1, MBindingTableContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBindingTableContainer_RootContext(), this.getBindingContext(), null, "rootContext", null, 0, //$NON-NLS-1$
				-1, MBindingTableContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
