		initEReference(getUIElement_Parent(), g1, this.getElementContainer_Children(), "parent", null, 0, 1, //$NON-NLS-1$
				MUIElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUIElement_ContainerData(), ecorePackage.getEString(), "containerData", null, 0, 1, //$NON-NLS-1$
				MUIElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getUIElement_CurSharedRef(), theAdvancedPackage.getPlaceholder(), null, "curSharedRef", null, 0, //$NON-NLS-1$
				1, MUIElement.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getUIElement_VisibleWhen(), this.getExpression(), null, "visibleWhen", null, 0, 1, //$NON-NLS-1$
				MUIElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUIElement_AccessibilityPhrase(), ecorePackage.getEString(), "accessibilityPhrase", null, 0, 1, //$NON-NLS-1$
				MUIElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getUIElement_LocalizedAccessibilityPhrase(), ecorePackage.getEString(),
				"localizedAccessibilityPhrase", null, 0, 1, MUIElement.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, //$NON-NLS-1$
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEOperation(getUIElement__UpdateLocalization(), null, "updateLocalization", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(elementContainerEClass, MElementContainer.class, "ElementContainer", IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
