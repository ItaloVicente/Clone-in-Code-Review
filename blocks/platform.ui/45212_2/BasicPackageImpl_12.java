
		initEClass(dialogElementEClass, MDialogElement.class, "DialogElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(wizardElementEClass, MWizardElement.class, "WizardElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(frameEClass, MFrame.class, "Frame", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getFrame_X(), ecorePackage.getEInt(), "x", "-2147483648", 0, 1, MFrame.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getFrame_Y(), ecorePackage.getEInt(), "y", "-2147483648", 0, 1, MFrame.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getFrame_Width(), ecorePackage.getEInt(), "width", "-1", 0, 1, MFrame.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getFrame_Height(), ecorePackage.getEInt(), "height", "-1", 0, 1, MFrame.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
