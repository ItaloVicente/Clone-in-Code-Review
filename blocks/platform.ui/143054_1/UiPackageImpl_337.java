		initEAttribute(getInput_InputURI(), ecorePackage.getEString(), "inputURI", null, 0, 1, MInput.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(uiElementEClass, MUIElement.class, "UIElement", IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUIElement_Widget(), ecorePackage.getEJavaObject(), "widget", null, 0, 1, MUIElement.class, //$NON-NLS-1$
				IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getUIElement_Renderer(), ecorePackage.getEJavaObject(), "renderer", null, 0, 1, MUIElement.class, //$NON-NLS-1$
				IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getUIElement_ToBeRendered(), ecorePackage.getEBoolean(), "toBeRendered", "true", 0, 1, //$NON-NLS-1$//$NON-NLS-2$
				MUIElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getUIElement_OnTop(), ecorePackage.getEBoolean(), "onTop", null, 0, 1, MUIElement.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUIElement_Visible(), ecorePackage.getEBoolean(), "visible", "true", 0, 1, MUIElement.class, //$NON-NLS-1$//$NON-NLS-2$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
