		initEAttribute(getUILabel_Label(), ecorePackage.getEString(), "label", null, 0, 1, MUILabel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getUILabel_IconURI(), ecorePackage.getEString(), "iconURI", null, 0, 1, MUILabel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getUILabel_Tooltip(), ecorePackage.getEString(), "tooltip", null, 0, 1, MUILabel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getUILabel_LocalizedLabel(), ecorePackage.getEString(), "localizedLabel", "", 0, 1, MUILabel.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getUILabel_LocalizedTooltip(), ecorePackage.getEString(), "localizedTooltip", "", 0, 1, MUILabel.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		initEClass(genericStackEClass, MGenericStack.class, "GenericStack", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(genericTileEClass, MGenericTile.class, "GenericTile", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getGenericTile_Horizontal(), ecorePackage.getEBoolean(), "horizontal", null, 0, 1, MGenericTile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(genericTrimContainerEClass, MGenericTrimContainer.class, "GenericTrimContainer", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getGenericTrimContainer_Side(), this.getSideValue(), "side", null, 1, 1, MGenericTrimContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(expressionEClass, MExpression.class, "Expression", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(coreExpressionEClass, MCoreExpression.class, "CoreExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getCoreExpression_CoreExpressionId(), ecorePackage.getEString(), "coreExpressionId", "", 0, 1, MCoreExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getCoreExpression_CoreExpression(), ecorePackage.getEJavaObject(), "coreExpression", null, 0, 1, MCoreExpression.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(imperativeExpressionEClass, MImperativeExpression.class, "ImperativeExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getImperativeExpression_Tracking(), ecorePackage.getEBoolean(), "tracking", null, 0, 1, MImperativeExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(snippetContainerEClass, MSnippetContainer.class, "SnippetContainer", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSnippetContainer_Snippets(), this.getUIElement(), null, "snippets", null, 0, -1, MSnippetContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(localizableEClass, MLocalizable.class, "Localizable", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
