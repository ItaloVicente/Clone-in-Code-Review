		initEAttribute(getInput_InputURI(), ecorePackage.getEString(), "inputURI", null, 0, 1, MInput.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(uiElementEClass, MUIElement.class, "UIElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getUIElement_Widget(), ecorePackage.getEJavaObject(), "widget", null, 0, 1, MUIElement.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getUIElement_Renderer(), ecorePackage.getEJavaObject(), "renderer", null, 0, 1, MUIElement.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getUIElement_ToBeRendered(), ecorePackage.getEBoolean(), "toBeRendered", "true", 0, 1, MUIElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getUIElement_OnTop(), ecorePackage.getEBoolean(), "onTop", null, 0, 1, MUIElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getUIElement_Visible(), ecorePackage.getEBoolean(), "visible", "true", 0, 1, MUIElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
