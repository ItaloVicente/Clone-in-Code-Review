		initEClass(partDescriptorEClass, MPartDescriptor.class, "PartDescriptor", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPartDescriptor_AllowMultiple(), ecorePackage.getEBoolean(), "allowMultiple", null, 0, 1, //$NON-NLS-1$
				MPartDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getPartDescriptor_Category(), ecorePackage.getEString(), "category", null, 0, 1, //$NON-NLS-1$
				MPartDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getPartDescriptor_Menus(), theMenuPackage.getMenu(), null, "menus", null, 0, -1, //$NON-NLS-1$
				MPartDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPartDescriptor_Toolbar(), theMenuPackage.getToolBar(), null, "toolbar", null, 0, 1, //$NON-NLS-1$
				MPartDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPartDescriptor_Closeable(), ecorePackage.getEBoolean(), "closeable", "false", 0, 1, //$NON-NLS-1$//$NON-NLS-2$
				MPartDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getPartDescriptor_Dirtyable(), ecorePackage.getEBoolean(), "dirtyable", null, 0, 1, //$NON-NLS-1$
				MPartDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getPartDescriptor_ContributionURI(), ecorePackage.getEString(), "contributionURI", null, 0, 1, //$NON-NLS-1$
				MPartDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getPartDescriptor_Description(), ecorePackage.getEString(), "description", null, 0, 1, //$NON-NLS-1$
				MPartDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getPartDescriptor_LocalizedDescription(), ecorePackage.getEString(), "localizedDescription", //$NON-NLS-1$
				null, 0, 1, MPartDescriptor.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getPartDescriptor_Variables(), ecorePackage.getEString(), "variables", null, 0, -1, //$NON-NLS-1$
				MPartDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, !IS_ORDERED);
		initEReference(getPartDescriptor_Properties(), theApplicationPackage.getStringToStringMap(), null, "properties", //$NON-NLS-1$
				null, 0, -1, MPartDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPartDescriptor_TrimBars(), theBasicPackage_1.getTrimBar(), null, "trimBars", null, 0, -1, //$NON-NLS-1$
				MPartDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getPartDescriptor__UpdateLocalization(), null, "updateLocalization", 0, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		initEClass(partDescriptorContainerEClass, MPartDescriptorContainer.class, "PartDescriptorContainer", //$NON-NLS-1$
				IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPartDescriptorContainer_Descriptors(), this.getPartDescriptor(), null, "descriptors", null, 0, //$NON-NLS-1$
				-1, MPartDescriptorContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
