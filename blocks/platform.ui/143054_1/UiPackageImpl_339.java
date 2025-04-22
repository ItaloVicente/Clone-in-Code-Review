		initEAttribute(getUILabel_Label(), ecorePackage.getEString(), "label", null, 0, 1, MUILabel.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUILabel_IconURI(), ecorePackage.getEString(), "iconURI", null, 0, 1, MUILabel.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUILabel_Tooltip(), ecorePackage.getEString(), "tooltip", null, 0, 1, MUILabel.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUILabel_LocalizedLabel(), ecorePackage.getEString(), "localizedLabel", "", 0, 1, //$NON-NLS-1$//$NON-NLS-2$
				MUILabel.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				IS_DERIVED, IS_ORDERED);
		initEAttribute(getUILabel_LocalizedTooltip(), ecorePackage.getEString(), "localizedTooltip", "", 0, 1, //$NON-NLS-1$//$NON-NLS-2$
				MUILabel.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				IS_DERIVED, IS_ORDERED);

		initEClass(genericStackEClass, MGenericStack.class, "GenericStack", IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(genericTileEClass, MGenericTile.class, "GenericTile", IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGenericTile_Horizontal(), ecorePackage.getEBoolean(), "horizontal", null, 0, 1, //$NON-NLS-1$
				MGenericTile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(genericTrimContainerEClass, MGenericTrimContainer.class, "GenericTrimContainer", IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGenericTrimContainer_Side(), this.getSideValue(), "side", null, 1, 1, //$NON-NLS-1$
				MGenericTrimContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(expressionEClass, MExpression.class, "Expression", IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(coreExpressionEClass, MCoreExpression.class, "CoreExpression", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCoreExpression_CoreExpressionId(), ecorePackage.getEString(), "coreExpressionId", "", 0, 1, //$NON-NLS-1$//$NON-NLS-2$
				MCoreExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getCoreExpression_CoreExpression(), ecorePackage.getEJavaObject(), "coreExpression", null, 0, 1, //$NON-NLS-1$
				MCoreExpression.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(imperativeExpressionEClass, MImperativeExpression.class, "ImperativeExpression", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getImperativeExpression_Tracking(), ecorePackage.getEBoolean(), "tracking", null, 0, 1, //$NON-NLS-1$
				MImperativeExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(snippetContainerEClass, MSnippetContainer.class, "SnippetContainer", IS_ABSTRACT, IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSnippetContainer_Snippets(), this.getUIElement(), null, "snippets", null, 0, -1, //$NON-NLS-1$
				MSnippetContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(localizableEClass, MLocalizable.class, "Localizable", IS_ABSTRACT, IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
