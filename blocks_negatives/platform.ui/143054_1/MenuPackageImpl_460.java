		initEClass(toolBarElementEClass, MToolBarElement.class, "ToolBarElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(toolControlEClass, MToolControl.class, "ToolControl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(handledToolItemEClass, MHandledToolItem.class, "HandledToolItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(directToolItemEClass, MDirectToolItem.class, "DirectToolItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(toolBarSeparatorEClass, MToolBarSeparator.class, "ToolBarSeparator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(menuContributionsEClass, MMenuContributions.class, "MenuContributions", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getMenuContributions_MenuContributions(), this.getMenuContribution(), null, "menuContributions", null, 0, -1, MMenuContributions.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(toolBarContributionEClass, MToolBarContribution.class, "ToolBarContribution", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getToolBarContribution_ParentId(), ecorePackage.getEString(), "parentId", null, 0, 1, MToolBarContribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getToolBarContribution_PositionInParent(), ecorePackage.getEString(), "positionInParent", null, 0, 1, MToolBarContribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(toolBarContributionsEClass, MToolBarContributions.class, "ToolBarContributions", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getToolBarContributions_ToolBarContributions(), this.getToolBarContribution(), null, "toolBarContributions", null, 0, -1, MToolBarContributions.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(trimContributionEClass, MTrimContribution.class, "TrimContribution", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getTrimContribution_ParentId(), ecorePackage.getEString(), "parentId", null, 0, 1, MTrimContribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getTrimContribution_PositionInParent(), ecorePackage.getEString(), "positionInParent", null, 0, 1, MTrimContribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(trimContributionsEClass, MTrimContributions.class, "TrimContributions", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getTrimContributions_TrimContributions(), this.getTrimContribution(), null, "trimContributions", null, 0, -1, MTrimContributions.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(dynamicMenuContributionEClass, MDynamicMenuContribution.class, "DynamicMenuContribution", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
